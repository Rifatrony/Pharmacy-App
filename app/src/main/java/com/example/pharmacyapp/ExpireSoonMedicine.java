package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpireSoonMedicine extends AppCompatActivity {


    EditText purchaseSearch;

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ExpireSoonAdapter adapter;
    ArrayList<purchaseMedicineDataHolder> list;
    ArrayList<String> expireDateList;
    ArrayList<String> purchaseUidList;

    FirebaseUser user;
    String todayDate, tomorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expire_soon_medicine);

        this.setTitle("Expire Soon");

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        purchaseSearch = findViewById(R.id.searchExpireMedicine);
        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.expireSoonRecyclerView);

        purchaseSearch.addTextChangedListener(new TextWatcher() {
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


        expireDateList = new ArrayList<>();
        purchaseUidList = new ArrayList<>();

        fetchExpireSoonMedicine();


        //setExpireMedicineFromPurchase();
    }

    private void fetchExpireSoonMedicine() {

        long currentDateAndTimeProvider = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        todayDate = dateFormat.format(currentDateAndTimeProvider);

        System.out.println("TODAY's date is --> "+ todayDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +2);

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String afterThreeDaysDate = date.format(calendar.getTime());
        System.out.println("After 3 days date is---------->"+afterThreeDaysDate);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, +1);
        SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
        tomorrow = date1.format(calendar1.getTime());
        System.out.println("Tomorrows days date is---------->"+tomorrow);

        user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Purchase Medicine");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new ExpireSoonAdapter(this,list);
        recyclerView.setAdapter(adapter);

        //Query query = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Purchase Medicine").orderByChild("s_medicine_name").endBefore(afterThreeDaysDate);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot data : snapshot.getChildren()){
                    purchaseMedicineDataHolder obj = data.getValue(purchaseMedicineDataHolder.class);

                    if (todayDate.equals(obj.getExpire_date()) || tomorrow.equals(obj.getExpire_date()) || afterThreeDaysDate.equals(obj.getExpire_date())){

                        list.add(obj);
                        System.out.println("Expire date is===========>>"+obj.getExpire_date());

                    }

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
        ArrayList<purchaseMedicineDataHolder> filteredList = new ArrayList<>();

        for (purchaseMedicineDataHolder item : list) {
            if (item.getS_medicine_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }


    /*private void setExpireMedicineFromPurchase() {

        long currentDateAndTimeProvider = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        todayDate = dateFormat.format(currentDateAndTimeProvider);

        System.out.println("TODAY's date is --> "+ todayDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +3);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        afterThreeDaysDate = sdf.format(calendar.getTime());
        System.out.println("After 3 days date--------->"+afterThreeDaysDate);



    }*/
}