package com.training.wafi.UserAppAccess;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.training.wafi.Home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.training.wafi.R;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPassword = findViewById(R.id.editPassword);
        TextView txtCancel = findViewById(R.id.txtCancel);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Intent mainIntent = new Intent(this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(WelcomeActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnSignUp.setOnClickListener(v -> {
            // Navigate to SignUpActivity
            Intent signUpIntent = new Intent(this, SignUpActivity.class);
            startActivity(signUpIntent);
        });

        txtCancel.setOnClickListener(v -> {
            // Optional: Perform any necessary cleanup and exit the app or navigate to another page
        });
    }
}
