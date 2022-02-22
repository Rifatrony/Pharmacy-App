package com.example.pharmacyapp;

public class addAccountDataHolder {

    String bank_name, account_name, account_number,account_type, branch, opening_balance, uid;

    public addAccountDataHolder() {

    }

    public addAccountDataHolder(String bank_name, String account_name, String account_number, String account_type, String branch, String opening_balance, String uid) {
        this.bank_name = bank_name;
        this.account_name = account_name;
        this.account_number = account_number;
        this.account_type = account_type;
        this.branch = branch;
        this.opening_balance = opening_balance;
        this.uid = uid;

    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getOpening_balance() {
        return opening_balance;
    }

    public void setOpening_balance(String opening_balance) {
        this.opening_balance = opening_balance;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
