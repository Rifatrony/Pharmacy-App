package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class SubDeshboard extends AppCompatActivity implements View.OnClickListener {

    private CardView medicineCategoryCardView, medicineTypeCardView, medicineUnitCardView,
            genericNameCardView, manufactureCardView, medicineListCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_deshboard);


        //set title
        this.setTitle(R.string.medicine);

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        medicineCategoryCardView = findViewById(R.id.medicineCategoryCardViewId);
        medicineTypeCardView = findViewById(R.id.medicineTypeCardViewId);
        medicineUnitCardView = findViewById(R.id.medicineUnitCardViewId);
        genericNameCardView = findViewById(R.id.genericNameCardViewId);
        manufactureCardView = findViewById(R.id.manufactureCardViewId);
        medicineListCardView = findViewById(R.id.medicineListCardViewId);

        medicineCategoryCardView.setOnClickListener(this);
        medicineTypeCardView.setOnClickListener(this);
        medicineUnitCardView.setOnClickListener(this);
        genericNameCardView.setOnClickListener(this);
        manufactureCardView.setOnClickListener(this);
        medicineListCardView.setOnClickListener(this);

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
                Intent intent = new Intent(this,ShowMedicineCategory.class);
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
            if (view.getId()==R.id.medicineListCardViewId){
                Intent intent = new Intent(this,ViewMedicineActivity.class);
                startActivity(intent);
            }
        }
}