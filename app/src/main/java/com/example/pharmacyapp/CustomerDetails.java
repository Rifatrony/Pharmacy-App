package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.pharmacyapp.Adapter.CustomerAdapter;
import com.example.pharmacyapp.Model.CustomerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerDetails extends AppCompatActivity {

    RecyclerView customerDetails;
    EditText customerSearchEditText;

    DatabaseReference dbCustomer;
    CustomerAdapter adapter;
    ArrayList<CustomerModel> list;


    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        this.setTitle("Customer Details");

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customerDetails = findViewById(R.id.customerDetailsRecyclerView);
        customerSearchEditText = findViewById(R.id.customerSearchId);

        user = FirebaseAuth.getInstance().getCurrentUser();

        dbCustomer = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Customer");

        customerDetails.setHasFixedSize(true);
        customerDetails.setLayoutManager(new LinearLayoutManager(CustomerDetails.this));

        list = new ArrayList<>();
        adapter = new CustomerAdapter(this,list);
        customerDetails.setAdapter(adapter);

        dbCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    CustomerModel pd = data.getValue(CustomerModel.class);
                    list.add(pd);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        customerSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
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

    private void filter(String text) {
        ArrayList<CustomerModel> filteredList = new ArrayList<>();

        for (CustomerModel item : list) {
            if (item.getCustomer_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }

}