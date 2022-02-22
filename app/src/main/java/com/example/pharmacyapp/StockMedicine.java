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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StockMedicine extends AppCompatActivity {

    EditText stockSearch;
    DatabaseReference dbStock;
    StockAdapter adapter;
    ArrayList<StockMedicineDataHolder> list;
    RecyclerView stockRecyclerView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_medicine);

        this.setTitle(R.string.stock);
        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        stockRecyclerView = findViewById(R.id.stockRecyclerView);
        stockSearch = findViewById(R.id.searchStock);
        stockSearch.addTextChangedListener(new TextWatcher() {
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

        user = FirebaseAuth.getInstance().getCurrentUser();

        dbStock = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Stock Medicine");

        stockRecyclerView.setHasFixedSize(true);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new StockAdapter(getApplicationContext(),list);
        stockRecyclerView.setAdapter(adapter);

        dbStock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    StockMedicineDataHolder pd = data.getValue(StockMedicineDataHolder.class);
                    list.add(pd);
                }

                adapter.notifyDataSetChanged();
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

    private void filter(String text) {
        ArrayList<StockMedicineDataHolder> filteredList = new ArrayList<>();

        for (StockMedicineDataHolder item : list) {
            if (item.getMedicineName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }
}