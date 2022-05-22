package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pharmacyapp.Model.StockMedicineModel;
import com.example.pharmacyapp.Model.SupplierDataHolder;
import com.example.pharmacyapp.Model.TransactionModel;
import com.example.pharmacyapp.Model.AddAccountModel;
import com.example.pharmacyapp.Model.AddMedicineModel;
import com.example.pharmacyapp.Model.purchaseMedicineModel;
import com.example.pharmacyapp.databinding.ActivityPurchaseMedicineBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class PurchaseMedicine extends AppCompatActivity {

    /*DatePickerDialog.OnDateSetListener setListener;*/


    ActivityPurchaseMedicineBinding binding;

    Spinner manufactureSpinner, medicineNameSpinner, paymentTypeSpinner, supplierSpinner;
    TextView stockQuantityTextView, totalPrice, dueAmount, accountBalance;
    String selectedMedicineUid = null;
    String selectedSupplierUid = null;
    String selectedAccountUid = null;
    String purchase_Uid;
    String supplier_uid;
    String supplierUid;
    String transaction_Uid;
    String todayDate;
    AddAccountModel data1;

    DatabaseReference dbSupplier;
    SupplierDataHolder data;
    //double dueFromDb = 0;
    //double finalDue = 0;
    //boolean isValid = true;
    double mainBalance = 0;
    double updatedBalance = 0;
    double singleAccountBalance = 0;
    DatabaseReference balanceRef;



    EditText expireDateEditText, buyDateEditText;
    TextInputLayout batchIdEditText,
            quantityEditText, manufacturePriceEditText, purchasePaidAmount;

    Button saveButton;
    FirebaseUser user;
    //String storeQuantity;

    DatabaseReference db, dbAccount, dbStock, dbref, databaseReference, dbTv;

    ArrayList<String> menufacturelist;

    ArrayList<String> supplierList;
    ArrayList<String> supplierUidList;

    ArrayList<String> medicineNamelist;
    ArrayList<String> medicineUidList;

    ArrayList<String> manufacturerPriceList;

    ArrayList<String> paymentTypeList;
    ArrayList<String> accountBalanceList;
    ArrayList<String> accountUidList;

    ArrayAdapter<String> menufactureadapter;
    ArrayAdapter<String> supplierAdapter;
    ArrayAdapter<String> medicineNameadapter;
    ArrayAdapter<String> paymentTypeAdapter;

    double recentMedicineQuantity;
    double finalMedicineQuantity;
    double recentAccountBalance;

    //ArrayAdapter<String> stockMedicineNameAdapter;


    int medicineStockFromDb;
    double supplierFinalDue;
    double selectedSupplierDueFromDb;
    double recentDue;
    double updatedAccountBalance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseMedicineBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_purchase_medicine);

        getMedicineStockFormDb();



        this.setTitle("Purchase");

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        manufactureSpinner = findViewById(R.id.spinnerManufactureId);
        medicineNameSpinner = findViewById(R.id.spinnerMedicineNameId);
        paymentTypeSpinner = findViewById(R.id.spinnerPaymentTypeId);
        supplierSpinner = findViewById(R.id.spinnerSuppilerId);
        saveButton = findViewById(R.id.saveButtonId);

        //storeQuantityTextView = findViewById(R.id.storeQuantityId);

        buyDateEditText = findViewById(R.id.buyDateId);
        batchIdEditText = findViewById(R.id.batchId);
        expireDateEditText = findViewById(R.id.expierDateId);
        quantityEditText = findViewById(R.id.quantityId);
        dueAmount = findViewById(R.id.purchaseDueAmountId);
        purchasePaidAmount = findViewById(R.id.purchasPaidAmountId);
        manufacturePriceEditText = findViewById(R.id.manufacturePriceId);
        accountBalance = findViewById(R.id.accountBalanceTextViewID);

        totalPrice = findViewById(R.id.totalPriceId);
        stockQuantityTextView = findViewById(R.id.stockQuantityTextViewId);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        assert user != null;
        dbref = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Manufacture Name");

        db = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Medicine List");

        dbAccount = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Accounts");

        dbTv = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child(":Medicine").child("Medicine List");

        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Purchase Medicine");

        dbStock = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Stock Medicine");


        dbSupplier = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Supplier List");

        //Manufacture
        menufacturelist = new ArrayList<>();
        menufactureadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,menufacturelist);
        manufactureSpinner.setAdapter(menufactureadapter);

        supplierList = new ArrayList<>();
        supplierAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,supplierList);
        supplierSpinner.setAdapter(supplierAdapter);

        //Medicine Name
        medicineNamelist = new ArrayList<>();
        manufacturerPriceList = new ArrayList<>();
        medicineUidList = new ArrayList<>();
        supplierUidList = new ArrayList<>();

        accountUidList = new ArrayList<>();

        //stockQuantityList = new ArrayList<>();


        //Payment type
        paymentTypeList = new ArrayList<>();
        accountBalanceList = new ArrayList<>();

        // Date picker of buy medicine date

        buyDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PurchaseMedicine.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date;
                        month = month +1;

                        if (month < 10){
                            date = day+"/0"+month+"/"+year;
                            /*THIS PART*/
                            if (day < 10) {
                                date = "0"+day+"/0"+month+"/"+year;
                            }
                        }
                        else {
                            date = day+"/"+month+"/"+year;

                            /*THIS PART*/

                            if (day < 10) {
                                date = "0"+day+"/0"+month+"/"+year;
                            }
                        }
                        buyDateEditText.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        // Date picker of expire medicine date

        expireDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PurchaseMedicine.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date;
                        month = month +1;

                        if (month < 10){
                            date = day+"/0"+month+"/"+year;
                            /*THIS PART*/
                            if (day < 10) {
                                date = "0"+day+"/0"+month+"/"+year;
                            }
                        }
                        else {
                            date = day+"/"+month+"/"+year;

                            /*THIS PART*/

                            if (day < 10) {
                                date = "0"+day+"/0"+month+"/"+year;
                            }
                        }
                        expireDateEditText.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        fetchManufactureData();
        fetchSupplier();
        fetchMedicineNameData();
        fetchPaymentTypeData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPurchaseMedicine();
            }
        });

    }

    private void getMedicineStockFormDb() {
    }


    //Back button on top navbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }



    // fetch manufacture name through spinner
    private void fetchSupplier() {
        dbSupplier.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot supplierName : snapshot.getChildren()){

                    SupplierDataHolder data = supplierName.getValue(SupplierDataHolder.class);
                    if (data != null)
                    {
                        supplierList.add(data.getSupplierName());
                        supplierUidList.add(data.getSupplier_uid());
                    }
                    //supplierList.add(Objects.requireNonNull(menudata.getValue()).toString());
                }
                setSupplierAdapter(supplierList, supplierUidList);


                //supplierAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setSupplierAdapter(ArrayList<String> list, ArrayList<String> supplierUidList ) {
        supplierAdapter = new ArrayAdapter<>(PurchaseMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);
        supplierSpinner.setAdapter(supplierAdapter);


        supplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedSupplierUid = supplierUidList.get(i);

                user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Supplier List/"+selectedSupplierUid);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        SupplierDataHolder data = snapshot.getValue(SupplierDataHolder.class);

                        if (data != null) {

                            selectedSupplierDueFromDb = Double.parseDouble(data.getTotal_due());

                            System.out.println("Total due is --> "+selectedSupplierDueFromDb);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    // fetch manufacture name through spinner
    private void fetchManufactureData() {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot menudata : snapshot.getChildren())
                    menufacturelist.add(Objects.requireNonNull(menudata.getValue()).toString());

                menufactureadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // fetch medicine name

    private void fetchMedicineNameData() {

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot medicineNamedata : snapshot.getChildren()){

                    AddMedicineModel data = medicineNamedata.getValue(AddMedicineModel.class);
                    if (data != null)
                    {
                        medicineNamelist.add(data.getM_name());
                        manufacturerPriceList.add(data.getManufacture_price());
                        medicineUidList.add(data.getUid());
                    }
                }
                setMedicineAdapter(medicineNamelist, manufacturerPriceList, medicineUidList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                System.out.println("Failed Message ------------"+error.getMessage());
            }
        });
    }

    private void setMedicineAdapter(ArrayList<String> list, ArrayList<String> manufacturerPriceList, ArrayList<String> medicineUidList ) {
        medicineNameadapter = new ArrayAdapter<>(PurchaseMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);
        medicineNameSpinner.setAdapter(medicineNameadapter);

        medicineNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String medicineUid = medicineUidList.get(position);
                selectedMedicineUid = medicineUid;

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference quantityRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Stock Medicine/"+medicineUid);
                quantityRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            StockMedicineModel data = snapshot.getValue(StockMedicineModel.class);
                            if (data != null) {
                                stockQuantityTextView.setText(data.getStock_quantity());
                                recentMedicineQuantity = Double.parseDouble(data.getStock_quantity());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                String mPrice = manufacturerPriceList.get(position);

                manufacturePriceEditText.getEditText().setText(mPrice);

                double price = Double.parseDouble(manufacturePriceEditText.getEditText().getText().toString());

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!manufacturePriceEditText.getEditText().getText().toString().equals("") && !quantityEditText.getEditText().getText().toString().equals("")){

                            double temp1 = Double.parseDouble(quantityEditText.getEditText().getText().toString());
                            double res= price*temp1;

                            totalPrice.setText(String.valueOf(res));

                            TextWatcher textWatcher1 = new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    System.out.println("COMPILER CAME");

                                    if (!totalPrice.getText().toString().equals("") && !purchasePaidAmount.getEditText().getText().toString().equals("")) {

                                        System.out.println("COMPILER CAME INNER");
                                        double temp2 = Double.parseDouble(purchasePaidAmount.getEditText().getText().toString());
                                        double result = res - temp2;
                                        recentDue = result;
                                        dueAmount.setText(String.valueOf(result));

                                        System.out.println("MAIN BALANCE is -- "+recentAccountBalance);
                                        System.out.println("PAID IS -- "+charSequence.toString());

                                        double latestBalance = recentAccountBalance - Double.parseDouble(charSequence.toString());
                                        accountBalance.setText(String.valueOf(latestBalance));

                                        System.out.println("LATEST IS -- "+String.valueOf(latestBalance));

                                        if (charSequence.toString().equals("")) {
                                            fetchPaymentTypeData();

                                        }

                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                    // paid amount er edit text clear hole age balance thik moto show korar jonno....

                                    if (editable.toString().isEmpty()) {

                                        fetchPaymentTypeData();
                                        System.out.println("CHECKING IS NULL");

                                    }
                                }
                            };

                            totalPrice.addTextChangedListener(textWatcher1);
                            purchasePaidAmount.getEditText().addTextChangedListener(textWatcher1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        if (!editable.toString().equals("")) {

                            finalMedicineQuantity = recentMedicineQuantity + Double.parseDouble(editable.toString());
                            stockQuantityTextView.setText(String.valueOf(finalMedicineQuantity));

                            System.out.println("RECENT STOCk ----> "+finalMedicineQuantity);

                        } else {

                            System.out.println(" NULL  ");
                            stockQuantityTextView.setText(String.valueOf(recentMedicineQuantity));
                        }
                    }
                };

                manufacturePriceEditText.getEditText().addTextChangedListener(textWatcher);
                quantityEditText.getEditText().addTextChangedListener(textWatcher);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fetchPaymentTypeData() {
        dbAccount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot paymentType : snapshot.getChildren()){
                    AddAccountModel data = paymentType.getValue(AddAccountModel.class);
                    if (data != null){
                        paymentTypeList.add(data.getBank_name());
                        accountBalanceList.add(data.getOpening_balance());
                        accountUidList.add(data.getUid());
                    }
                }
                setAccountAdapter(paymentTypeList, accountBalanceList, accountUidList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAccountAdapter(ArrayList<String> paymentTypeList, ArrayList<String> accountBalanceList, ArrayList<String> accountUidList) {

        paymentTypeAdapter = new ArrayAdapter<>(PurchaseMedicine.this, android.R.layout.simple_spinner_dropdown_item,paymentTypeList);
        paymentTypeSpinner.setAdapter(paymentTypeAdapter);

        paymentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedAccountUid = accountUidList.get(i);
                String parAccountBalance = accountBalanceList.get(i);
                //selectedAccountUid = parAccountBalance;
                recentAccountBalance = Double.parseDouble(parAccountBalance);
                System.out.println("Selected Account Uid----->"+selectedAccountUid);

                accountBalance.setText(String.valueOf(recentAccountBalance));
                //singleAccountBalance = accountBalance;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //purchase medicine
    private void insertPurchaseMedicine() {

        supplierFinalDue = recentDue + selectedSupplierDueFromDb;

        DatabaseReference selectedSupplierRef = FirebaseDatabase.getInstance()
                .getReference(user.getUid()+"/Medicine/Supplier List/"+selectedSupplierUid);

        System.out.println("Last uid is -> "+selectedSupplierRef);

        HashMap <String, Object> map = new HashMap<>();
        map.put("total_due",String.valueOf(supplierFinalDue));
        selectedSupplierRef.updateChildren(map);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String key = dbStock.push().getKey();

        String s_manufacture = manufactureSpinner.getSelectedItem().toString();
        String s_medicine_name = medicineNameSpinner.getSelectedItem().toString();
        String s_supplier_name = supplierSpinner.getSelectedItem().toString();
        String buy_date = buyDateEditText.getText().toString().trim();
        //Add new
        String payment_type = paymentTypeSpinner.getSelectedItem().toString();
        String batch_id = batchIdEditText.getEditText().getText().toString().trim();
        String expire_date = expireDateEditText.getText().toString().trim();
        String quantity = quantityEditText.getEditText().getText().toString().trim();
        String manufacture_price = manufacturePriceEditText.getEditText().getText().toString().trim();
        String account_balance = accountBalance.getText().toString().trim();

        String total_price = totalPrice.getText().toString();
        String purchase_paid_amount = purchasePaidAmount.getEditText().getText().toString().trim();
        String purchase_due_amount = dueAmount.getText().toString().trim();



        purchase_Uid = databaseReference.push().getKey();

        /*updatedBalance = recentAccountBalance - Double.parseDouble(purchase_paid_amount);
        HashMap<String, Object> balanceMap = new HashMap<>();
        balanceMap.put("opening_balance", updatedBalance);
        dbAccount.child(selectedAccountUid).updateChildren(balanceMap);*/

        //Here will come stock quantity from stock table

        purchaseMedicineModel obj = new purchaseMedicineModel(s_manufacture, s_medicine_name, buy_date, payment_type, batch_id, expire_date, quantity, manufacture_price, total_price, purchase_paid_amount, purchase_due_amount, purchase_Uid, s_supplier_name, account_balance);

        assert user != null;
        databaseReference= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;
        databaseReference.child("Medicine").child("Purchase Medicine").child(purchase_Uid).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                long currentDateAndTimeProvider = System.currentTimeMillis();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                todayDate = dateFormat.format(currentDateAndTimeProvider);

                System.out.println("TODAY's date is --> "+ todayDate);

                String supplierUid = new SupplierDataHolder().getSupplier_uid();
                String transactionUid = new TransactionModel().getUid();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference stockRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Transaction/");

                transaction_Uid = stockRef.push().getKey();

                stockRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Transaction/"+transaction_Uid);

                TransactionModel data = new TransactionModel(transaction_Uid,"Paid supplier when Purchase Medicine", todayDate,purchase_paid_amount,payment_type,s_supplier_name );
                stockRef.setValue(data);
            }
        });

        //  StockMedicineDataHolder data = new StockMedicineDataHolder(manufactureName, medicineName, buyDate, paymentType, batchId, expireDate, total_quantity, manufacturePrice, total_Price, key);

        assert user != null;
        dbStock= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;

        HashMap<String, Object> stockMap = new HashMap<>();

        stockMap.put("stock_quantity", stockQuantityTextView.getText().toString());

        dbStock.child("Medicine").child("Stock Medicine").child(selectedMedicineUid).updateChildren(stockMap);

        System.out.println("Selected account uid is ---->"+ selectedAccountUid);


        if (buy_date.isEmpty()){
            buyDateEditText.setError("Required");
            buyDateEditText.requestFocus();
            return;
        }

        if (batch_id.isEmpty()){
            batchIdEditText.setError("Required");
            batchIdEditText.requestFocus();
            return;
        }

        if (batch_id.isEmpty()){
            batchIdEditText.setError("Required");
            batchIdEditText.requestFocus();
            return;
        }

        if (expire_date.isEmpty()){
            batchIdEditText.setError("Required");
            batchIdEditText.requestFocus();
            return;
        }

        if (quantity.isEmpty()){
            quantityEditText.setError("Required");
            quantityEditText.requestFocus();
            return;
        }

        if (manufacture_price.isEmpty()){
            manufacturePriceEditText.setError("Required");
            manufacturePriceEditText.requestFocus();
            return;
        }


        // paid amount er edit text clear hole age balance thik moto show korar jonno....

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference selectedAccountRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Accounts/"+selectedAccountUid);
        double accountFinalBalance = recentAccountBalance - Double.parseDouble(purchase_paid_amount.trim());

        HashMap<String, Object> balanceMap = new HashMap<>();
        balanceMap.put("opening_balance", String.valueOf(accountFinalBalance));
        selectedAccountRef.updateChildren(balanceMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    finish();

                }
            }
        });

        Toast.makeText(getApplicationContext(),"Medicine Purchase Successfully",Toast.LENGTH_SHORT).show();
    }
}