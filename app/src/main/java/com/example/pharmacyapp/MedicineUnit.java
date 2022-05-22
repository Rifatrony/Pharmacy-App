package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pharmacyapp.Model.MedicineUnitModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicineUnit extends AppCompatActivity {

    EditText medicineUnitEditText;
    Button saveBtn;
    DatabaseReference dbMedicineUnit;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_unit);

        //set title
        setTitle("Medicine Unit");

        //add back button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        medicineUnitEditText = findViewById(R.id.medicineUnitId);
        saveBtn = findViewById(R.id.medicineUnitSaveButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        dbMedicineUnit = FirebaseDatabase.getInstance().getReference(user.getUid());
        dbMedicineUnit.child("Medicine").child("Medicine Unit");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMedicineUnit();
            }
        });

    }

    private void insertMedicineUnit() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        uid = dbMedicineUnit.push().getKey();

        String medicine_unit = medicineUnitEditText.getText().toString().trim();

        MedicineUnitModel obj = new MedicineUnitModel(medicine_unit, uid);

        dbMedicineUnit = FirebaseDatabase.getInstance().getReference(user.getUid());
        dbMedicineUnit.child("Medicine").child("Medicine Unit").child(uid).setValue(obj);

        medicineUnitEditText.setText("");

        /*Intent intent = new Intent(this,ShowNoteActivity.class);
        startActivity(intent);*/


        Toast.makeText(getApplicationContext(),"Medicine Unit Added",Toast.LENGTH_LONG).show();




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}