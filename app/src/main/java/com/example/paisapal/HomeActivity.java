package com.example.paisapal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button transactionButton, transactionHistoryButton, viewProfileButton, aboutUsButton;
    private TextView totalIncomeTextView, totalSpentTextView, balanceTextView, userName;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);

        transactionButton = findViewById(R.id.button_transaction);
        transactionHistoryButton = findViewById(R.id.button_transaction_history);
        viewProfileButton = findViewById(R.id.button_view_profile);
        totalIncomeTextView = findViewById(R.id.text_total_income);
        totalSpentTextView = findViewById(R.id.text_total_spent);
        balanceTextView = findViewById(R.id.text_balance);
        userName = findViewById(R.id.text_user_name);
        aboutUsButton = findViewById(R.id.button_about_us);

        // Get the username and email from the intent
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");

        String greeting = "Hello " + db.getUserName() + ", Welcome to PaisaPal!";
        userName.setText(greeting);

        // Navigate to ProfileActivity
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the username and email to ProfileActivity
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        displayTotals();

        // Navigate to AddTransactionActivity
        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to TransactionHistoryActivity
        transactionHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TransactionHistoryActivity.class);
                startActivity(intent);
            }
        });

        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to display totals (this remains unchanged)
    private void displayTotals() {
        double totalIncome;
        totalIncome = db.getTotalIncome(db.getUserName());
        double totalSpent;
        totalSpent = db.getTotalAmountSpent(db.getUserName());

        // Update TextViews with the calculated totals
        totalIncomeTextView.setText("Total Income:  " + String.format("%.2f", totalIncome));
        totalIncomeTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        totalSpentTextView.setText("Total Spent:   " + String.format("%.2f", totalSpent));
        totalSpentTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        balanceTextView.setText("Total Balance: "+String.format("%.2f",(totalIncome-totalSpent)));
    }
}
