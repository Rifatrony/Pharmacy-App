package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SupplierRadioActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_radio);

        this.setTitle(R.string.supplier);

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        radioGroup = findViewById(R.id.radioGroup);

    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        switch (radioId){
            case R.id.radio_one:
                Intent intent = new Intent(getApplicationContext(), SupplierActivity.class);
                startActivity(intent);
                break;

            case R.id.radio_two:
                Intent intent1 = new Intent(getApplicationContext(), ViewAllSupplierList.class);
                startActivity(intent1);
                break;

            case R.id.radio_three:
                Intent intent2 = new Intent(getApplicationContext(), ViewDueSupplier.class);
                startActivity(intent2);
                break;
        }

        /*Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
                Toast.LENGTH_SHORT).show();*/
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