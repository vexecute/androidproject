package com.example.paisapal;

public class Transaction {

    private String description;
    private String category;
    private String type; // "income" or "expense"
    private double amount;
    private String date; // New field for storing the date

    // Constructor now includes date
    public Transaction(String description, String category, String type, double amount, String date) {
        this.description = description;
        this.category = category;
        this.type = type;
        this.amount = amount;
        this.date = date; // Initialize the date field
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Getter for category
    public String getCategory() {
        return category;
    }

    // Getter for type
    public String getType() {
        return type;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }

    // Getter for date
    public String getDate() {
        return date; // New method to get the date
    }
}
