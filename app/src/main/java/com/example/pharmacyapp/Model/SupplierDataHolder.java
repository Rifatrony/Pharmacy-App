package com.example.pharmacyapp.Model;

public class SupplierDataHolder {

    String supplierName, supplier_uid, total_due, supplier_contact;

    public SupplierDataHolder() {
    }


    public SupplierDataHolder(String supplierName, String supplier_uid, String total_due, String supplier_contact) {
        this.supplierName = supplierName;
        this.supplier_uid = supplier_uid;
        this.total_due = total_due;
        this.supplier_contact = supplier_contact;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplier_uid() {
        return supplier_uid;
    }

    public void setSupplier_uid(String supplier_uid) {
        this.supplier_uid = supplier_uid;
    }

    public String getTotal_due() {
        return total_due;
    }

    public void setTotal_due(String total_due) {
        this.total_due = total_due;
    }

    public String getSupplier_contact() {
        return supplier_contact;
    }

    public void setSupplier_contact(String supplier_contact) {
        this.supplier_contact = supplier_contact;
    }
}
