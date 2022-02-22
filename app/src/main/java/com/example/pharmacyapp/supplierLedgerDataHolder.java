package com.example.pharmacyapp;

public class supplierLedgerDataHolder {

    String purchase_Uid, supplierUid, t_quantity;

    public supplierLedgerDataHolder() {
    }

    public supplierLedgerDataHolder(String purchase_Uid, String supplierUid, String t_quantity) {
        this.purchase_Uid = purchase_Uid;
        this.supplierUid = supplierUid;
        this.t_quantity = t_quantity;
    }


    public String getPurchase_Uid() {
        return purchase_Uid;
    }

    public void setPurchase_Uid(String purchase_Uid) {
        this.purchase_Uid = purchase_Uid;
    }

    public String getSupplierUid() {
        return supplierUid;
    }

    public void setSupplierUid(String supplierUid) {
        this.supplierUid = supplierUid;
    }

    public String getT_quantity() {
        return t_quantity;
    }

    public void setT_quantity(String t_quantity) {
        this.t_quantity = t_quantity;
    }
}
