package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.pharmacyapp.Adapter.TransactionAdapter;
import com.example.pharmacyapp.Model.TransactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionDetails extends AppCompatActivity {

    EditText searchTransaction;
    RecyclerView transactionRecyclerView;
    DatabaseReference dbTransaction;
    TransactionAdapter adapter;
    ArrayList<TransactionModel> list;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        this.setTitle("Transaction Details");

        transactionRecyclerView = findViewById(R.id.transactionRecyclerView);
        searchTransaction = findViewById(R.id.searchTransactionId);

        searchTransaction.addTextChangedListener(new TextWatcher() {
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

        fetchTransactionData();
    }

    private void fetchTransactionData() {

        user = FirebaseAuth.getInstance().getCurrentUser();

        dbTransaction = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Transaction");

        transactionRecyclerView.setHasFixedSize(true);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new TransactionAdapter(getApplicationContext(),list);
        transactionRecyclerView.setAdapter(adapter);

        dbTransaction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    TransactionModel obj = data.getValue(TransactionModel.class);
                    list.add(obj);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filter(String text) {
        ArrayList<TransactionModel> filteredList = new ArrayList<>();

        for (TransactionModel item : list) {
            if (item.getDate().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }
}