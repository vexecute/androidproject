package com.example.paisapal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private EditText filterText;
    private ListView transactionListView;
    private TransactionAdapter transactionAdapter;
    private List<Transaction> transactionList;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        filterText = findViewById(R.id.filter_text);
        transactionListView = findViewById(R.id.transaction_list);
        db = new DatabaseHelper(this);

        // Retrieve all transactions from the database
        transactionList = db.getAllTransactions(db.getUserName());
        transactionAdapter = new TransactionAdapter(this, transactionList);
        transactionListView.setAdapter(transactionAdapter);

        // Set up a text watcher for filtering transactions
        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                transactionAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
