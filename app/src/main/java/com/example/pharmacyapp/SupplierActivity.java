package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pharmacyapp.databinding.ActivitySupplierBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SupplierActivity extends AppCompatActivity {

    EditText supplierEditText, supplierContactEditText;
    Button supplierSaveButton;
    DatabaseReference dbSupplier;


    String uid;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ActivitySupplierBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupplierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.setTitle(R.string.addSupplier);

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        supplierEditText = findViewById(R.id.supplierEditTextId);
        supplierContactEditText = findViewById(R.id.supplierContactEditTextId);
        supplierSaveButton = findViewById(R.id.supplierButtonId);

        dbSupplier = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Supplier List");

        supplierSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertSupplier();
            }
        });

    }

    //Back button on top navbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertSupplier() {
        String data = supplierEditText.getText().toString().trim();
        String data1 = supplierContactEditText.getText().toString().trim();

        uid = dbSupplier.push().getKey();

        SupplierDataHolder obj = new SupplierDataHolder(data, uid,"0",data1);

        DatabaseReference stockRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Supplier List/"+uid);
        stockRef.setValue(obj);

        Toast.makeText(getApplicationContext(), "Supplier Added Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,ViewAllSupplierList.class);
        startActivity(intent);
        finish();
        supplierEditText.setText("");
        supplierContactEditText.setText("");
    }
}