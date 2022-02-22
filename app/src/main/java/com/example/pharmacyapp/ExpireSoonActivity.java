package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpireSoonActivity extends AppCompatActivity {
    TextView formDateTextView, toDateTextView;
    RecyclerView expireSoonRecyclerView;
    showExpireSoonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expire_soon);

        this.setTitle(R.string.expire_soon);

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        formDateTextView = findViewById(R.id.fromDateTextViewId);
        toDateTextView = findViewById(R.id.toDateTextViewId);
        expireSoonRecyclerView = findViewById(R.id.expireSoonRecyclerViewId);

        //Set current date
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        formDateTextView.setText(currentDate);

        //Set date of 30 days from today
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, +1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String nextMonthDate = sdf.format(calendar.getTime());
        toDateTextView.setText(nextMonthDate);

        expireSoonRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<StockMedicineDataHolder> options =
                new FirebaseRecyclerOptions.Builder<StockMedicineDataHolder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Stock Medicine")
                                .orderByChild("medicineName").startAt(currentDate),StockMedicineDataHolder.class).build();
        adapter = new showExpireSoonAdapter(options);
        expireSoonRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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