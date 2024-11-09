package com.example.paisapal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Transaction> transactionList; // Original list
    private List<Transaction> filteredList; // Filtered list

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
        this.filteredList = transactionList; // Initialize filtered list with the original list
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.transaction_list_item, parent, false);
        }

        Transaction transaction = (Transaction) getItem(position);

        TextView description = convertView.findViewById(R.id.transactionDescription);
        TextView date = convertView.findViewById(R.id.transactionDate);
        TextView category = convertView.findViewById(R.id.transactionCategory);
        TextView amount = convertView.findViewById(R.id.transactionAmount);

        // Set values for each field
        description.setText(transaction.getDescription());
        date.setText(transaction.getDate());
        category.setText(transaction.getCategory());
        amount.setText(String.valueOf(transaction.getAmount()));

        if (transaction.getType().equalsIgnoreCase("income")) {
            amount.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark)); // Set red color
        } else {
            amount.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark)); // Set green color
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                List<Transaction> filteredResults = new ArrayList<>();

                if (filterString.isEmpty()) {
                    filteredResults.addAll(transactionList); // No filter applied
                } else {
                    for (Transaction transaction : transactionList) {
                        if (transaction.getDescription().toLowerCase().contains(filterString) ||
                                transaction.getCategory().toLowerCase().contains(filterString)) {
                            filteredResults.add(transaction);
                        }
                    }
                }

                results.values = filteredResults;
                results.count = filteredResults.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<Transaction>) results.values;
                notifyDataSetChanged(); // Notify the adapter to refresh the list
            }
        };
    }
}
