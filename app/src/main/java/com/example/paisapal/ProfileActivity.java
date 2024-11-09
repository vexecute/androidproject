package com.example.paisapal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTextView, emailTextView;
    private Button editProfileButton, budgetManagementButton;

    private static final int EDIT_PROFILE_REQUEST_CODE = 1;  // Request code for EditProfileActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTextView = findViewById(R.id.text_name);
        emailTextView = findViewById(R.id.text_email);
        editProfileButton = findViewById(R.id.button_edit_profile);
        budgetManagementButton = findViewById(R.id.button_budget_management);

        // Retrieve username and email from Intent
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");

        // Set the username and email in the TextViews
        nameTextView.setText(username);
        emailTextView.setText(email);

        // Navigate to Edit Profile page on button click
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);  // Start EditProfileActivity for result
        });
    }

    // Handle the result from EditProfileActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get the updated username and email from the result
            String updatedUsername = data.getStringExtra("updatedUsername");
            String updatedEmail = data.getStringExtra("updatedEmail");

            // Update the TextViews with the new data
            nameTextView.setText(updatedUsername);
            emailTextView.setText(updatedEmail);
        }
    }
}
