package com.example.pharmacyapp;

public class SellMedicineDataHolder {

    String customer_name, medicine_name, sell_date, payment_type, stock_quantity,
            sell_quantity, unit_sell_price, total_price, paid_amount, due_amount;


    public SellMedicineDataHolder() {
    }

    public SellMedicineDataHolder(String customer_name, String medicine_name, String sell_date, String payment_type, String stock_quantity, String sell_quantity, String unit_sell_price, String total_price, String paid_amount, String due_amount) {
        this.customer_name = customer_name;
        this.medicine_name = medicine_name;
        this.sell_date = sell_date;
        this.payment_type = payment_type;
        this.stock_quantity = stock_quantity;
        this.sell_quantity = sell_quantity;
        this.unit_sell_price = unit_sell_price;
        this.total_price = total_price;
        this.paid_amount = paid_amount;
        this.due_amount = due_amount;
    }


    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getSell_date() {
        return sell_date;
    }

    public void setSell_date(String sell_date) {
        this.sell_date = sell_date;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getSell_quantity() {
        return sell_quantity;
    }

    public void setSell_quantity(String sell_quantity) {
        this.sell_quantity = sell_quantity;
    }

    public String getUnit_sell_price() {
        return unit_sell_price;
    }

    public void setUnit_sell_price(String unit_sell_price) {
        this.unit_sell_price = unit_sell_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(String due_amount) {
        this.due_amount = due_amount;
    }
}
