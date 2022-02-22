package com.example.pharmacyapp;

public class addNoteDataholder {

    String title;
    String description, note_uid;

    public addNoteDataholder() {
    }

    public addNoteDataholder(String title, String description, String note_uid) {
        this.title = title;
        this.description = description;
        this.note_uid = note_uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote_uid() {
        return note_uid;
    }

    public void setNote_uid(String note_uid) {
        this.note_uid = note_uid;
    }
}
