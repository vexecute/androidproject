package com.example.paisapal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText descriptionEditText, amountEditText;
    private Spinner categorySpinner;
    private RadioGroup transactionTypeGroup;
    private Button addTransactionButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        db = new DatabaseHelper(this);

        descriptionEditText = findViewById(R.id.transaction_description);
        amountEditText = findViewById(R.id.transaction_amount);
        categorySpinner = findViewById(R.id.transaction_category);
        transactionTypeGroup = findViewById(R.id.transaction_type_group);
        addTransactionButton = findViewById(R.id.add_transaction_button);

        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionEditText.getText().toString().trim();
                String amount = amountEditText.getText().toString().trim();
                String category = categorySpinner.getSelectedItem().toString();
                int selectedTypeId = transactionTypeGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedTypeId);
                String type = selectedRadioButton.getText().toString();

                // Get the current date in the desired format
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                if (TextUtils.isEmpty(description) || TextUtils.isEmpty(amount)) {
                    Toast.makeText(AddTransactionActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    boolean result = db.addTransaction(description, category, type, Double.parseDouble(amount), date,db.getUserName()); // Pass date
                    if (result) {
                        Toast.makeText(AddTransactionActivity.this, "Transaction added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddTransactionActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  // Optional: Clears activity stack
                        startActivity(intent);
                        finish();
                        // Redirect to dashboard or refresh data
                    } else {
                        Toast.makeText(AddTransactionActivity.this, "Failed to add transaction", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
