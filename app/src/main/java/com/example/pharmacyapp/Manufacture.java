package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Manufacture extends AppCompatActivity {

    TextInputLayout manufactureEditText;
    Button manufactureSaveButton;
    DatabaseReference dbref;

    ValueEventListener listener;
    ArrayList<String> manufacturelist;
    ArrayAdapter<String> manufactureadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacture);

        //set title
        setTitle("Manufacture");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        manufactureEditText = findViewById(R.id.manufactureEditTextId);

        manufactureSaveButton = findViewById(R.id.manufactureSaveId);


        dbref = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Manufacture Name");

        manufactureSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveManufactureData();
                insertData();
            }
        });

    }



    //add back button tool bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    //Insert manufacture data to firebase
    private void insertData() {
        String data=manufactureEditText.getEditText().getText().toString().trim();

        dbref.push().setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        manufactureEditText.getEditText().setText("");
                        Toast.makeText(getApplicationContext(), "Inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /*private void saveManufactureData() {

        String key = dbref.push().getKey();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String manuName = manufactureEditText.getText().toString().trim();

        manufactureDataHolder obj = new manufactureDataHolder(manuName);
        FirebaseDatabase dbref= FirebaseDatabase.getInstance();
        DatabaseReference node = dbref.getReference(user.getUid());
        node.child("Medicine").child("Manufacture Name").setValue(obj);


        manufactureEditText.setText("");
        Toast.makeText(getApplicationContext(), "Inserted Successful", Toast.LENGTH_LONG).show();
    }*/
}