package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.pharmacyapp.Adapter.NoteAdapter;
import com.example.pharmacyapp.Model.AddNoteModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowNoteActivity extends AppCompatActivity {

    RecyclerView recview;
    NoteAdapter adapter;
    FloatingActionButton fb;
    ArrayList<AddNoteModel> list;
    DatabaseReference dbNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        this.setTitle(R.string.note);

        //Add back Button on tool bar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        recview = findViewById(R.id.recview);
        fb = findViewById(R.id.fadd);


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
                startActivity(intent);
            }
        });

        fetchNote();
    }

    private void fetchNote() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        dbNote = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Note Details");

        recview.setLayoutManager(new LinearLayoutManager(this));
        recview.setHasFixedSize(true);

        list = new ArrayList<>();
        adapter = new NoteAdapter(this,list);
        recview.setAdapter(adapter);

        dbNote.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot data : snapshot.getChildren()){
                    AddNoteModel pd = data.getValue(AddNoteModel.class);
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

        fetchNote();

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