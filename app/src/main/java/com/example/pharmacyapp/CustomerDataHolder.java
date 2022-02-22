package com.example.pharmacyapp;

public class CustomerDataHolder {

    String customer_name, customer_contact, customer_address, uid, total_due;

    public CustomerDataHolder() {
    }

    public CustomerDataHolder(String customer_name, String customer_contact, String customer_address, String uid, String total_due) {
        this.customer_name = customer_name;
        this.customer_contact = customer_contact;
        this.customer_address = customer_address;
        this.uid = uid;
        this.total_due = total_due;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_contact() {
        return customer_contact;
    }

    public void setCustomer_contact(String customer_contact) {
        this.customer_contact = customer_contact;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }
}
