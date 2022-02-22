package com.example.pharmacyapp;

public class MedicineUnitDataHolder {

    String medicine_unit, uid;

    public MedicineUnitDataHolder() {
    }

    public MedicineUnitDataHolder(String medicine_unit, String uid) {
        this.medicine_unit = medicine_unit;
        this.uid = uid;
    }


    public String getMedicine_unit() {
        return medicine_unit;
    }

    public void setMedicine_unit(String medicine_unit) {
        this.medicine_unit = medicine_unit;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
