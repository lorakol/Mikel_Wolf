package com.training.wafi.Truck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.training.wafi.FirebaseHelper;
import com.training.wafi.QRCodeGenerator;
import com.training.wafi.R;
import android.graphics.Bitmap;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        EditText editItemName = findViewById(R.id.editItemName);
        EditText editItemDescription = findViewById(R.id.editItemDescription);
        EditText editCostBeforeTax = findViewById(R.id.editCostBeforeTax);
        EditText editManufacturer = findViewById(R.id.editManufacturer);
        EditText editQuantity = findViewById(R.id.editQuantity);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnBack = findViewById(R.id.btnBack);

        btnSave.setOnClickListener(v -> {
            String itemName = editItemName.getText().toString();
            String itemDescription = editItemDescription.getText().toString();
            double costBeforeTax = Double.parseDouble(editCostBeforeTax.getText().toString());
            String manufacturer = editManufacturer.getText().toString();

            double tax = calculateTax(costBeforeTax);
            double total = calculateTotal(costBeforeTax, tax);

            String dateAdded = getCurrentDate();
            String addedBy = getCurrentUser();

            Item item = new Item(itemName, itemDescription, costBeforeTax, tax, total, dateAdded, addedBy, manufacturer);

            //load quantity
            int quantity = Integer.parseInt(editQuantity.getText().toString());
            item.setQuantity(quantity);

            Bitmap qrCodeBitmap = QRCodeGenerator.generateQRCode(itemName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference qrCodeRef = storageRef.child("qrcodes/" + item.getId() + ".png");
            UploadTask uploadTask = qrCodeRef.putBytes(data);

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    task.getException().printStackTrace(); // Log the exception
                    return null;
                }
                return qrCodeRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    item.setQrCodeUrl(task.getResult().toString());

                    // Add item to Firebase Database
                    FirebaseHelper firebaseHelper = new FirebaseHelper(this);
                    firebaseHelper.addItem(item);
                } else {
                    Log.e("AddItemActivity", "Failed to upload QR Code and get the download URL");
                }
            });

            Intent intent = new Intent(this, TruckActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private String getCurrentUser() {
        return "user";
    }

    private String getCurrentDate() {
        return "##/##/####";
    }

    private double calculateTotal(double costBeforeTax, double tax) {
        return costBeforeTax * (1 + tax);
    }

    private double calculateTax(double costBeforeTax) {
        return 0;
    }
}
