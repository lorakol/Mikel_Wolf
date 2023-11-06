package com.training.wafi.UserAppAccess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.training.wafi.Home.MainActivity;
import com.training.wafi.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, organizationEditText, truckNumberEditText, passwordEditText;
    private Spinner roleSpinner;
    private Button saveButton, cancelButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        initializeUI();

        saveButton.setOnClickListener(v -> {
            if (validateInput()) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                User user = createUser();
                                saveUserToFirebase(user);
                                saveUserLocally(user);
                                navigateToMainActivity();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        cancelButton.setOnClickListener(v -> navigateToWelcomeActivity());
    }

    private void initializeUI() {
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        organizationEditText = findViewById(R.id.organizationEditText);
        truckNumberEditText = findViewById(R.id.truckNumberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        roleSpinner = findViewById(R.id.roleSpinner);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRole = (String) parent.getItemAtPosition(position);
                truckNumberEditText.setVisibility("Tech".equals(selectedRole) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing here
            }
        });
    }

    private boolean validateInput() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String organization = organizationEditText.getText().toString().trim();
        String truckNumber = truckNumberEditText.getText().toString().trim();

        // Validate first name
        if (firstName.isEmpty()) {
            Toast.makeText(this, "First name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate last name
        if (lastName.isEmpty()) {
            Toast.makeText(this, "Last name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate email
        if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate password
        if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check password strength
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate organization
        if (organization.isEmpty()) {
            Toast.makeText(this, "Organization cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate truck number if the role is Tech
        String selectedRole = roleSpinner.getSelectedItem().toString();
        if ("Tech".equals(selectedRole) && truckNumber.isEmpty()) {
            Toast.makeText(this, "Truck number cannot be empty for Tech role", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private User createUser() {
        return new User(
                firstNameEditText.getText().toString(),
                lastNameEditText.getText().toString(),
                emailEditText.getText().toString(),
                organizationEditText.getText().toString(),
                roleSpinner.getSelectedItem().toString(),
                truckNumberEditText.getText().toString()
        );
    }

    private void saveUserToFirebase(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        usersRef.child(user.getEmail().replace(".", ",")).setValue(user);
    }

    private void saveUserLocally(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", user.getEmail());
        editor.putString("role", user.getRole());
        editor.apply();
    }

    private void navigateToMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void navigateToWelcomeActivity() {
        Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(welcomeIntent);
        finish();
    }
}
