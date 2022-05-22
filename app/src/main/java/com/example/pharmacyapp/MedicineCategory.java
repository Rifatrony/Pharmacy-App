package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pharmacyapp.Model.MedicineCategoryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicineCategory extends AppCompatActivity {

    EditText medicine_category;
    Button saveMedicine_category;
    DatabaseReference dbMedicineCategory;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_category);

        this.setTitle("Medicine Category");
        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        medicine_category = findViewById(R.id.medicineCategory);
        saveMedicine_category = findViewById(R.id.saveMedicineCategory);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        dbMedicineCategory = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine Category");

        saveMedicine_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMedicineCategory();
            }
        });

    }

    private void insertMedicineCategory() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        uid = dbMedicineCategory.push().getKey();

        String category = medicine_category.getText().toString().trim();


        MedicineCategoryModel obj = new MedicineCategoryModel(category, uid);

        dbMedicineCategory= FirebaseDatabase.getInstance().getReference(user.getUid());
        dbMedicineCategory.child("Medicine").child("Medicine Category").child(uid).setValue(obj);

        finish();
        medicine_category.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}