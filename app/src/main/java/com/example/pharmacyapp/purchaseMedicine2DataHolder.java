package com.example.pharmacyapp;

public class purchaseMedicine2DataHolder {

    String buy_date, expire_date, medicine_quantity;

    public purchaseMedicine2DataHolder() {

    }

    public purchaseMedicine2DataHolder(String buy_date, String expire_date, String medicine_quantity) {
        this.buy_date = buy_date;
        this.expire_date = expire_date;
        this.medicine_quantity = medicine_quantity;
    }

    public String getBuy_date() {
        return buy_date;
    }

    public void setBuy_date(String buy_date) {
        this.buy_date = buy_date;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public String getMedicine_quantity() {
        return medicine_quantity;
    }

    public void setMedicine_quantity(String medicine_quantity) {
        this.medicine_quantity = medicine_quantity;
    }
}
