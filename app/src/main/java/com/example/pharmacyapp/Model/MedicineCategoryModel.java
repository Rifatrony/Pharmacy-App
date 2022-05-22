package com.example.pharmacyapp.Model;

public class MedicineCategoryModel {
    String category, uid;

    public MedicineCategoryModel() {
    }

    public MedicineCategoryModel(String category, String uid) {
        this.category = category;
        this.uid = uid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
