package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoteActivity extends AppCompatActivity {

    EditText titleEditText, descriptionEditText;
    Button saveNote;
    DatabaseReference db1;
    String note_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.setTitle(R.string.add_Note);

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //add back button tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        titleEditText = findViewById(R.id.titleEditTextId);
        descriptionEditText = findViewById(R.id.descriptionEditTextId);

        saveNote = findViewById(R.id.saveNoteId);


        db1 = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Note Details");

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertNote();
            }
        });
    }



    //add back button tool bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertNote() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        note_uid = db1.push().getKey();

        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();


        if (title.isEmpty()){
            titleEditText.setError("Required Title");
            titleEditText.requestFocus();
            return;
        }

        if (description.isEmpty()){
            descriptionEditText.setError("Required Title");
            descriptionEditText.requestFocus();
            return;
        }

        addNoteDataholder obj = new addNoteDataholder(title, description, note_uid);

        db1= FirebaseDatabase.getInstance().getReference(user.getUid());
        db1.child("Medicine").child("Note Details").child(note_uid).setValue(obj);

        titleEditText.setText("");
        descriptionEditText.setText("");

        Intent intent = new Intent(getApplicationContext(),ShowNoteActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Note Added",Toast.LENGTH_LONG).show();
        finish();

    }


}