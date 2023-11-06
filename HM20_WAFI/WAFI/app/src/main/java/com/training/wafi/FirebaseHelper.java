package com.training.wafi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.training.wafi.Truck.Item;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private Context context;

    public FirebaseHelper(Context context) {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference("items");
        mStorage = FirebaseStorage.getInstance().getReference("qrcodes");
    }

    public void addItem(Item item) {
        String itemId = mDatabase.push().getKey();
        Bitmap qrCode = QRCodeGenerator.generateQRCode(itemId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        qrCode.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        // Upload to Firebase Storage
        StorageReference qrCodeRef = mStorage.child(itemId + ".png");
        UploadTask uploadTask = qrCodeRef.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> qrCodeRef.getDownloadUrl().addOnSuccessListener(uri -> {
            // Save item with QR code URL to Firebase Database
            item.setQrCodeUrl(uri.toString());
            mDatabase.child(itemId).setValue(item).addOnSuccessListener(aVoid -> {
                // On success, save the item locally
                saveItemLocally(item);
            }).addOnFailureListener(e -> {
                // Handle failure in saving to Firebase Database
                Log.e("FirebaseHelper", "Failed to save item to Firebase: ", e);
            });
        })).addOnFailureListener(e -> {
            // Handle failure in uploading QR code to Firebase Storage
            Log.e("FirebaseHelper", "Failed to upload QR code to Firebase Storage: ", e);
        });
    }

    public void loadData(final DataStatus dataStatus) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Item> items = new ArrayList<>();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    if (item != null) {
                        items.add(item);
                        Log.d("FirebaseHelper", "Item Name: " + item.getName());
                    }
                }
                dataStatus.DataIsLoaded(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log the error
                Log.e("FirebaseHelper", "Database Error: " + databaseError.getMessage());
            }
        });
    }

    private void saveItemLocally(Item item) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("local_items", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonItem = gson.toJson(item);
        editor.putString(item.getId(), jsonItem);
        editor.apply();
    }

    public interface DataStatus {
        void DataIsLoaded(List<Item> items);

        void DataIsLoaded(List<Item> items, List<String> keys);
    }
}
