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

public class GenericName extends AppCompatActivity {

    EditText genericNameEditText;
    Button genericNameSaveButton;
    DatabaseReference dbref;

    ValueEventListener listener;
    ArrayList<String> genericNamelist;
    ArrayAdapter<String> genericNameadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_name);

        //set title
        this.setTitle("Generic Name");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Add back button tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        genericNameEditText = findViewById(R.id.genericNameEditTextId);

        genericNameSaveButton = findViewById(R.id.genericNameSaveButtonId);


        dbref = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Generic Name");

        genericNameSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
    }

    //Add back button tool bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertData() {
        String data=genericNameEditText.getText().toString().trim();

        dbref.push().setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        genericNameEditText.setText("");

                        Toast.makeText(getApplicationContext(), "Inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}