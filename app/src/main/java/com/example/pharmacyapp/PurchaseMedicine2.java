package com.example.pharmacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PurchaseMedicine2 extends AppCompatActivity {

    EditText butDate, expireDate,quantity;
    Button saveBtn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_medicine2);
        this.setTitle("Purchase Medicine");

        butDate= findViewById(R.id.buydateid);
        expireDate= findViewById(R.id.expiredateid);
        quantity= findViewById(R.id.quantityid);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Purchase Medicine");
        saveBtn= findViewById(R.id.savebtnid);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPurchaseMedicine();
            }
        });

    }

    private void insertPurchaseMedicine() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        String buy_date = butDate.getText().toString().trim();
        String expire_date = expireDate.getText().toString().trim();
        String medicine_quantity = quantity.getText().toString().trim();

        String key = databaseReference.push().getKey();

        purchaseMedicine2DataHolder obj = new purchaseMedicine2DataHolder(buy_date, expire_date,medicine_quantity);

        databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid());
        databaseReference.child("Medicine").child("Purchase Medicine").child(key).setValue(obj);

        butDate.setText("");
        expireDate.setText("");
        quantity.setText("");

        /*Intent intent = new Intent(getApplicationContext(),ShowNoteActivity.class);
        startActivity(intent);*/

        Toast.makeText(getApplicationContext(),"Medicine Purchase Successfully",Toast.LENGTH_LONG).show();


    }
}