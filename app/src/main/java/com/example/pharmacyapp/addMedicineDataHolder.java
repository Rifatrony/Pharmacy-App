package com.example.pharmacyapp;

public class addMedicineDataHolder {
    String m_name,box_pattern,m_category,m_unit,sell_price,
            manufacture_price,shelf_no,s_manufacture,m_type,
            s_genericName, uid;

    addMedicineDataHolder(){

    }

    public addMedicineDataHolder(String m_name, String box_pattern, String m_category, String m_unit, String sell_price,
                                 String manufacture_price, String shelf_no, String s_manufacture, String m_type,
                                 String s_genericName, String uid) {
        this.m_name = m_name;
        this.box_pattern = box_pattern;
        this.m_category = m_category;
        this.m_unit = m_unit;
        this.sell_price = sell_price;
        this.manufacture_price = manufacture_price;
        this.shelf_no = shelf_no;
        this.s_manufacture = s_manufacture;
        this.m_type = m_type;
        this.s_genericName = s_genericName;
        this.uid = uid;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getBox_pattern() {
        return box_pattern;
    }

    public void setBox_pattern(String box_pattern) {
        this.box_pattern = box_pattern;
    }

    public String getM_category() {
        return m_category;
    }

    public void setM_category(String m_category) {
        this.m_category = m_category;
    }

    public String getM_unit() {
        return m_unit;
    }

    public void setM_unit(String m_unit) {
        this.m_unit = m_unit;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getManufacture_price() {
        return manufacture_price;
    }

    public void setManufacture_price(String manufacture_price) {
        this.manufacture_price = manufacture_price;
    }

    public String getShelf_no() {
        return shelf_no;
    }

    public void setShelf_no(String shelf_no) {
        this.shelf_no = shelf_no;
    }

    public String getS_manufacture() {
        return s_manufacture;
    }

    public void setS_manufacture(String s_manufacture) {
        this.s_manufacture = s_manufacture;
    }

    public String getM_type() {
        return m_type;
    }

    public void setM_type(String m_type) {
        this.m_type = m_type;
    }

    public String getS_genericName() {
        return s_genericName;
    }

    public void setS_genericName(String s_genericName) {
        this.s_genericName = s_genericName;
    }
}
