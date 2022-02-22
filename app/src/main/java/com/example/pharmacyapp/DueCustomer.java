package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DueCustomer extends AppCompatActivity {

    RecyclerView dueCustomerRecyclerView;
    DatabaseReference dbCustomer;
    List<CustomerDataHolder> list;
    DueCustomerAdapter adapter;

    String uid;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_customer);

        this.setTitle("Due Customer");

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dueCustomerRecyclerView = findViewById(R.id.dueCustomerList);


        dbCustomer = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Customer");


        list = new ArrayList<>();
        dueCustomerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDueCustomerList();
    }

    private void fetchDueCustomerList() {

        dbCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    CustomerDataHolder data = snapshot1.getValue(CustomerDataHolder.class);
                    if (data != null && Double.parseDouble(data.getTotal_due()) > 0) {

                        list.add(data);

                    }
                }

                adapter = new DueCustomerAdapter(DueCustomer.this, list);
                dueCustomerRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
}