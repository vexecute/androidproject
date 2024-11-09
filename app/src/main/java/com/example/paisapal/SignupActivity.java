package com.example.paisapal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signupButton, loginButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.signup_usernameEditText);
        emailEditText = findViewById(R.id.signup_emailEditText);
        passwordEditText = findViewById(R.id.signup_passwordEditText);
        confirmPasswordEditText = findViewById(R.id.signup_confirmPasswordEditText);
        signupButton = findViewById(R.id.signup_button);
        loginButton = findViewById(R.id.signup_loginButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    boolean result = db.insertUser(username, email, password);
                    if (result) {
                        Toast.makeText(SignupActivity.this, "Sign up successful. Please login.", Toast.LENGTH_SHORT).show();
                        // Redirect to login page after successful sign up
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);  // Change this to MainActivity
                        startActivity(intent);
                        finish();  // Close the signup activity
                    } else {
                        Toast.makeText(SignupActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Login page
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });
    }
}
