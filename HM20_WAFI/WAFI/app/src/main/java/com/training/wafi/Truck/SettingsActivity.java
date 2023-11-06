package com.training.wafi.Truck;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.training.wafi.R;


public class SettingsActivity extends AppCompatActivity {
    EditText editBar1, editBar2, editBar3;
    Button btnSend;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressData progData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        editBar1 = findViewById(R.id.progress_1);
        editBar2 = findViewById(R.id.progress_2);
        editBar3 = findViewById(R.id.progress_3);
        btnSend = findViewById(R.id.idBtnSendData);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ProgressBar");
        progData = new ProgressData();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    private void updateData(){
        progData.progress_1 = Integer.parseInt(editBar1.getText().toString());
        progData.progress_2 = Integer.parseInt(editBar2.getText().toString());
        progData.progress_3 = Integer.parseInt(editBar3.getText().toString());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.

                databaseReference.setValue(progData);

                // after adding this data we are showing toast message.
                Toast.makeText(SettingsActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(SettingsActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
