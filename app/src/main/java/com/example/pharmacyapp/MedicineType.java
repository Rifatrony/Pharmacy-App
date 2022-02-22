package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedicineType extends AppCompatActivity {
    EditText medicineTypeEditText;
    Button medicineTypeSaveButton;
    DatabaseReference dbref;

    ValueEventListener listener;
    ArrayList<String> medicineTypelist;
    ArrayAdapter<String> medicineTypeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_type);

        this.setTitle("Medicine Type");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();;

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        medicineTypeEditText = findViewById(R.id.medicineTypeEditTextId);

        medicineTypeSaveButton = findViewById(R.id.medicineTypeSaveButtonId);


        dbref = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine Type");


        medicineTypeSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertdata();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertdata() {
        String data=medicineTypeEditText.getText().toString().trim();

        dbref.push().setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        medicineTypeEditText.setText("");

                        Toast.makeText(getApplicationContext(), "Inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                });



    }

}