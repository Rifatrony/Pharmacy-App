package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class MedicineAll extends AppCompatActivity implements View.OnClickListener {

    private CardView medicineCategoryCardView, medicineTypeCardView, medicineUnitCardView,
            genericNameCardView, manufactureCardView, addMedicineCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_all);

        //set title
        this.setTitle("Medicine");

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        medicineCategoryCardView = findViewById(R.id.medicineCategoryCardViewId);
        medicineTypeCardView = findViewById(R.id.medicineTypeCardViewId);
        medicineUnitCardView = findViewById(R.id.medicineUnitCardViewId);
        genericNameCardView = findViewById(R.id.genericNameCardViewId);
        manufactureCardView = findViewById(R.id.manufactureCardViewId);
        addMedicineCardView = findViewById(R.id.addMedicineCardViewId);

        medicineCategoryCardView.setOnClickListener(this);
        medicineTypeCardView.setOnClickListener(this);
        medicineUnitCardView.setOnClickListener(this);
        genericNameCardView.setOnClickListener(this);
        manufactureCardView.setOnClickListener(this);
        addMedicineCardView.setOnClickListener(this);


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.medicineCategoryCardViewId){
            Intent intent = new Intent(this,MedicineCategory.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.medicineTypeCardViewId){
            Intent intent = new Intent(this,MedicineType.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.medicineUnitCardViewId){
            Intent intent = new Intent(this,MedicineUnit.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.genericNameCardViewId){
            Intent intent = new Intent(this,GenericName.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.manufactureCardViewId){
            Intent intent = new Intent(this,Manufacture.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.addMedicineCardViewId){
            Intent intent = new Intent(this,AddMedicine.class);
            startActivity(intent);
        }
    }
}