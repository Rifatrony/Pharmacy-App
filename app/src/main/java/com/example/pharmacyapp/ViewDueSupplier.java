package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pharmacyapp.databinding.ActivitySupplierBinding;
import com.example.pharmacyapp.databinding.ActivityViewDueSupplierBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDueSupplier extends AppCompatActivity {


    DatabaseReference dbSupplier;
    List<SupplierDataHolder> list;
    SupplierAdapter adapter;

    String uid;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ActivityViewDueSupplierBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewDueSupplierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setContentView(R.layout.activity_view_due_supplier);

        this.setTitle(R.string.dueSupplier);

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbSupplier = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Supplier List");

        list = new ArrayList<>();
        binding.supplierList.setLayoutManager(new LinearLayoutManager(this));

        fetchSupplierList();

    }

    private void fetchSupplierList() {

        dbSupplier.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    SupplierDataHolder data = snapshot1.getValue(SupplierDataHolder.class);
                    if (data != null && Double.parseDouble(data.getTotal_due()) > 0) {

                        list.add(data);

                    }
                }

                adapter = new SupplierAdapter(ViewDueSupplier.this, list);
                binding.supplierList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //Back button on top navbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}