package com.example.pharmacyapp.Model;

public class TransactionModel {

    String uid, reason, date, amount, bank, name;

    public TransactionModel() {
    }

    public TransactionModel(String uid, String reason, String date, String amount, String bank, String name) {
        this.uid = uid;
        this.reason = reason;
        this.date = date;
        this.amount = amount;
        this.bank = bank;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
