package com.example.paisapal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName, editEmail, editOldPassword, editNewPassword, editConfirmNewPassword;
    private Button saveProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editOldPassword = findViewById(R.id.edit_old_password);
        editNewPassword = findViewById(R.id.edit_new_password);
        editConfirmNewPassword = findViewById(R.id.edit_confirm_new_password);
        saveProfileButton = findViewById(R.id.button_save_profile);

        // Retrieve username and email from Intent
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");

        // Set the username and email in EditText fields
        editName.setText(username);
        editEmail.setText(email);

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileChanges();
            }
        });
    }

    private void saveProfileChanges() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String oldPassword = editOldPassword.getText().toString().trim();
        String newPassword = editNewPassword.getText().toString().trim();
        String confirmNewPassword = editConfirmNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Name and Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // After saving profile changes, return the updated data to ProfileActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedUsername", name);
        resultIntent.putExtra("updatedEmail", email);
        setResult(RESULT_OK, resultIntent);  // Send the result back to ProfileActivity
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
