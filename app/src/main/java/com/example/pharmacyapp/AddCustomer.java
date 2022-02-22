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

public class AddCustomer extends AppCompatActivity {

    EditText customer_nameEditText, customer_contactEditText, customer_addressEditText;
    Button save_customer_button;
    String uid;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        this.setTitle("Add Customer");

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        customer_nameEditText = findViewById(R.id.customerNameId);
        customer_contactEditText = findViewById(R.id.customerContactId);
        customer_addressEditText = findViewById(R.id.customerAddressId);

        save_customer_button = findViewById(R.id.customerSaveBtnId);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Accounts");


        save_customer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCustomer();
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


    private void insertCustomer() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String customer_name = customer_nameEditText.getText().toString();
        String customer_contact = customer_contactEditText.getText().toString();
        String customer_address = customer_addressEditText.getText().toString();

        uid = db.push().getKey();

        CustomerDataHolder obj = new CustomerDataHolder(customer_name, customer_contact, customer_address, uid, "0");

        FirebaseDatabase addAccount = FirebaseDatabase.getInstance();
        DatabaseReference node = addAccount.getReference(user.getUid());

        node.child("Medicine").child("Customer").child(uid).setValue(obj);

        Toast.makeText(getApplicationContext(),"Customer Successfully",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, CustomerDetails.class);
        startActivity(intent);
        finish();
        customer_nameEditText.setText("");
        customer_contactEditText.setText("");
        customer_addressEditText.setText("");


    }



}