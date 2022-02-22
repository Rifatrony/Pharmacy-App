package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowMedicineCategory extends AppCompatActivity {

    RecyclerView medicineCategoryRecyclerView;
    FloatingActionButton medicineCategoryFab;

    DatabaseReference databaseReference;
    MedicineCategoryAdapter adapter;
    ArrayList<MedicineCategoryDataHolder> list;


    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicine_category);



        medicineCategoryRecyclerView = findViewById(R.id.medicineCategoryRecyclerView);
        medicineCategoryFab = findViewById(R.id.medicineCategoryfab);


        medicineCategoryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowMedicineCategory.this,MedicineCategory.class);

                startActivity(intent);
            }
        });

        fetchMedicineCategory();

    }

    private void fetchMedicineCategory() {

        user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine Category");

        medicineCategoryRecyclerView.setHasFixedSize(true);
        medicineCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new MedicineCategoryAdapter(this,list);
        medicineCategoryRecyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot data : snapshot.getChildren()){
                    MedicineCategoryDataHolder pd = data.getValue(MedicineCategoryDataHolder.class);
                    list.add(pd);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMedicineCategory();
    }
}