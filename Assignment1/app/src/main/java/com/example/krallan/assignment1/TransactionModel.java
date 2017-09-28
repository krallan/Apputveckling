package com.example.krallan.assignment1;

public class TransactionModel {
    private String transaction_type;
    private long date;
    private String title;
    private String category;
    private float amount;

    public TransactionModel(String transaction_type, long date, String title, String category, float amount) {
        this.transaction_type = transaction_type;
        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
