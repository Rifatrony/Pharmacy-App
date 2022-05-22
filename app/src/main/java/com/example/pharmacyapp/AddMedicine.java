package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pharmacyapp.Model.MedicineCategoryModel;
import com.example.pharmacyapp.Model.StockMedicineModel;
import com.example.pharmacyapp.Model.AddMedicineModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddMedicine extends AppCompatActivity {

    EditText medicineNameEditText, boxPatternEditText, medicineUnitEditText,
            sellPriceEditText, manufacturePriceEditText, shelfNumberEditText;

    Button addMedicineButton;


    Spinner menufacturespinner, gnspinner, medicinetypespinner, medicineCategorySpinner;

    DatabaseReference dbref;
    DatabaseReference db;
    DatabaseReference mtypedb;
    DatabaseReference databaseReference, dbMedicineCategory;

    ValueEventListener listener;
    ValueEventListener listener1;
    ValueEventListener listener2;

    ArrayList<String> menufacturelist;
    ArrayList<String> gnlist;
    ArrayList<String> mtypelist;
    ArrayList<String> medicineCategoryList;

    ArrayAdapter<String> menufactureadapter;
    ArrayAdapter<String> gnadapter;
    ArrayAdapter<String> mtypeadapter;
    ArrayAdapter<String> medicineCategoryAdapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String m_name ;
    String box_pattern ;
    String m_category;
    String m_unit;
    String sell_price ;
    String manufacture_price ;
    String shelf_no;

    String s_manufacture;
    String m_type;
    String s_genericName ;
    String uid ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        setTitle("Add Medicine");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //add back button tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        medicineNameEditText=findViewById(R.id.medicineNameEditTextId);
        boxPatternEditText=findViewById(R.id.boxpatternEditTextId);
        medicineUnitEditText=findViewById(R.id.medicineUnitEditTextId);
        sellPriceEditText=findViewById(R.id.sellPriceEditTextId);
        manufacturePriceEditText=findViewById(R.id.manufacturePriceEditTextId);
        shelfNumberEditText=findViewById(R.id.shelfNumberEditTextId);

        addMedicineButton = findViewById(R.id.addMedicineButtonId);


        menufacturespinner = findViewById(R.id.spinnerManufactureId);
        gnspinner = findViewById(R.id.spinnerGenericNameId);
        medicinetypespinner = findViewById(R.id.spinnerMedicineTypeId);
        medicineCategorySpinner = findViewById(R.id.medicineCategorySpinnerId);

        /*     String key = dbref.push().getKey();*/

        dbref = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Manufacture Name");
        db = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Generic Name");
        mtypedb = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine Type");
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine List");
        dbMedicineCategory = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine Category");

        //Manufacture
        menufacturelist = new ArrayList<>();
        menufactureadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,menufacturelist);
        menufacturespinner.setAdapter(menufactureadapter);


        //Generic Name
        gnlist = new ArrayList<>();
        gnadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,gnlist);
        gnspinner.setAdapter(gnadapter);

        //Medicine type
        mtypelist = new ArrayList<>();
        mtypeadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mtypelist);
        medicinetypespinner.setAdapter(mtypeadapter);

        //Medicine type
        medicineCategoryList = new ArrayList<>();
        medicineCategoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,medicineCategoryList);
        medicineCategorySpinner.setAdapter(medicineCategoryAdapter);


        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertMedicine();
            }
        });


        fetchManufactureData();
        fetchGenericName();
        fetchMedicineType();
        fetchMedicineCategory();
    }

    private void fetchMedicineCategory() {

        dbMedicineCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot medicineCategory : snapshot.getChildren()){

                    MedicineCategoryModel data = medicineCategory.getValue(MedicineCategoryModel.class);
                    if (data != null)
                    {
                        medicineCategoryList.add(data.getCategory());

                    }
                    //supplierList.add(Objects.requireNonNull(menudata.getValue()).toString());
                }
                setMedicineCategoryAdapter(medicineCategoryList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setMedicineCategoryAdapter(ArrayList<String> list) {

        medicineCategoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        medicineCategorySpinner.setAdapter(medicineCategoryAdapter);
    }


    //add back button tool bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void fetchManufactureData() {

        listener = dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot menudata : snapshot.getChildren())
                    menufacturelist.add(menudata.getValue().toString());

                menufactureadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void fetchGenericName() {
        listener1 = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot gndata : snapshot.getChildren())

                    gnlist.add(gndata.getValue().toString());

                gnadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void fetchMedicineType() {
        listener2 = mtypedb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mtypedata : snapshot.getChildren())

                    mtypelist.add(mtypedata.getValue().toString());

                mtypeadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void insertMedicine() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        m_name = medicineNameEditText.getText().toString().trim();
        box_pattern = boxPatternEditText.getText().toString().trim();
        m_unit = medicineUnitEditText.getText().toString().trim();
        sell_price = sellPriceEditText.getText().toString().trim();
        manufacture_price = manufacturePriceEditText.getText().toString().trim();
        shelf_no = shelfNumberEditText.getText().toString().trim();

        m_category = medicineCategorySpinner.getSelectedItem().toString().trim();
        s_manufacture = menufacturespinner.getSelectedItem().toString();
        m_type = medicinetypespinner.getSelectedItem().toString();
        s_genericName = gnspinner.getSelectedItem().toString();

        uid = databaseReference.push().getKey();

        if (m_name.isEmpty()){
            medicineNameEditText.setError("Required");
            medicineNameEditText.requestFocus();
            return;
        }
        if (box_pattern.isEmpty()){
            boxPatternEditText.setError("Required");
            boxPatternEditText.requestFocus();
            return;
        }

        if (m_unit.isEmpty()){
            medicineUnitEditText.setError("Required");
            medicineUnitEditText.requestFocus();
            return;
        }
        if (sell_price.isEmpty()){
            sellPriceEditText.setError("Required");
            sellPriceEditText.requestFocus();
            return;
        }
        if (manufacture_price.isEmpty()){
            manufacturePriceEditText.setError("Required");
            manufacturePriceEditText.requestFocus();
            return;
        }
        if (shelf_no.isEmpty()){
            shelfNumberEditText.setError("Required");
            shelfNumberEditText.requestFocus();
            return;
        }

        AddMedicineModel obj = new AddMedicineModel(m_name,box_pattern,m_category,m_unit,sell_price,
                manufacture_price,shelf_no,s_manufacture,m_type,s_genericName, uid);
        FirebaseDatabase addMedicinedb = FirebaseDatabase.getInstance();
        DatabaseReference node = addMedicinedb.getReference(user.getUid());

        node.child("Medicine").child("Medicine List").child(uid).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override

            public void onComplete(@NonNull Task<Void> task) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference stockRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Stock Medicine/"+uid);

                StockMedicineModel obj = new StockMedicineModel(s_manufacture, m_name,"","","","","0",manufacture_price, "", uid,m_unit, sell_price);
                stockRef.setValue(obj);
            }
        });

        medicineNameEditText.setText("");
        boxPatternEditText.setText("");
        medicineUnitEditText.setText("");
        sellPriceEditText.setText("");
        manufacturePriceEditText.setText("");
        shelfNumberEditText.setText("");
        menufacturespinner.getSelectedItem();
        gnspinner.getSelectedItem();
        medicinetypespinner.getSelectedItem();
        Toast.makeText(getApplicationContext(),"Medicine Added Successfully",Toast.LENGTH_LONG).show();

        finish();
    }
}