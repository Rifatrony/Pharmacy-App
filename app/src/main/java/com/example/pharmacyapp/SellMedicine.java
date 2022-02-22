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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SellMedicine extends AppCompatActivity {

    Spinner medicineNameSpinner, paymentTypeSpinner, customerNameSpinner;

    EditText sellDateEditText;
    TextInputLayout sellQuantityEditText, sellPriceEditText, sellPaidAmount;

    String selectedMedicineUid = null;
    String selectedAccountUid = null;
    double recentAccountBalance;

    EditText totalPrice, sellDueAmount;
    TextView stockQuantityTextView, accountBalance;

    Button sellButton;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String selectedCustomerUid = null;
    String transaction_Uid, todayDate;

    ArrayList<String> customerNameList;
    ArrayList<String> medicineNamelist;

    ArrayList<String> paymentTypeList;
    ArrayList<String> accountBalanceList;
    ArrayList<String> accountUidList;

    ArrayList<String> sellPriceList;
    ArrayList<String> medicineUidList;
    ArrayList<String> customerUidList;


    ArrayAdapter<String> medicineNameadapter;
    ArrayAdapter<String> customerNameAdapter;
    ArrayAdapter<String> paymentTypeAdapter;

    double recentMedicineQuantity;
    double finalMedicineQuantity;

    DatabaseReference db, dbAccount, dbSell, dbStock, dbCustomer;

    double customerFinalDue;
    double recentDue;

    double selectedCustomerDueFromDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_medicine);

        this.setTitle(R.string.sell);

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        medicineNameSpinner = findViewById(R.id.spinnerMedicineNameId);
        paymentTypeSpinner = findViewById(R.id.spinnerPaymentTypeId);
        customerNameSpinner = findViewById(R.id.spinnerCustomerNameId);

        sellDateEditText = findViewById(R.id.sellDateId);
        sellQuantityEditText = findViewById(R.id.sellQuantityId);
        sellPriceEditText = findViewById(R.id.unitSellPriceId);
        totalPrice = findViewById(R.id.totalSellPriceId);
        sellPaidAmount = findViewById(R.id.sellPaidAmountId);
        sellDueAmount = findViewById(R.id.sellDueAmountId);
        stockQuantityTextView = findViewById(R.id.stockQuantityId);
        accountBalance = findViewById(R.id.accountBalanceTextViewID);

        sellButton = findViewById(R.id.sellBtnId);

        db = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Medicine List");

        dbAccount = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Accounts");

        dbStock = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Stock Medicine");

        dbCustomer = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Customer");

        dbSell = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Sell Medicine");

        //Medicine Name
        customerNameList = new ArrayList<>();
        customerNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,customerNameList);
        customerNameSpinner.setAdapter(customerNameAdapter);

        medicineNamelist = new ArrayList<>();
        sellPriceList = new ArrayList<>();
        medicineUidList = new ArrayList<>();
        customerUidList = new ArrayList<>();

        //Payment type
        paymentTypeList = new ArrayList<>();
        accountUidList = new ArrayList<>();
        accountBalanceList = new ArrayList<>();

        double selectedSupplierDueFromDb;


        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellMedicine();
            }
        });

        sellDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SellMedicine.this, new DatePickerDialog.OnDateSetListener() {
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
                        sellDateEditText.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        fetchMedicineNameData();
        fetchPaymentTypeData();
        fetchCustomerName();
    }



    //Back button on top navbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchCustomerName() {


        dbCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot customer_name : snapshot.getChildren()){
                    CustomerDataHolder data = customer_name.getValue(CustomerDataHolder.class);
                    if (data!= null){
                        customerNameList.add(data.getCustomer_name());
                        customerUidList.add(data.getUid());
                    }
                }

                setCustomerAdapter(customerNameList, customerUidList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setCustomerAdapter(ArrayList<String> customerNameList, ArrayList<String> customerUidList) {

        customerNameAdapter = new ArrayAdapter<>(SellMedicine.this, android.R.layout.simple_spinner_dropdown_item, customerNameList);
        customerNameSpinner.setAdapter(customerNameAdapter);

        customerNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCustomerUid = customerUidList.get(i);
                System.out.println("Selected Customer uid is------>"+selectedCustomerUid);

                user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Customer/"+selectedCustomerUid);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        SupplierDataHolder data = snapshot.getValue(SupplierDataHolder.class);

                        if (data != null) {

                            selectedCustomerDueFromDb = Double.parseDouble(data.getTotal_due());

                            System.out.println("Total due is --> "+selectedCustomerDueFromDb);

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

    private void fetchMedicineNameData() {

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /*medicineNamelist.add(0,"Choose Medicine Name");*/

                for (DataSnapshot medicineNamedata : snapshot.getChildren()){

                    addMedicineDataHolder data = medicineNamedata.getValue(addMedicineDataHolder.class);

                    if (data != null)
                    {
                        medicineNamelist.add(data.getM_name());
                        sellPriceList.add(data.getSell_price());
                        medicineUidList.add(data.getUid());
                    }
                }
                setMedicineAdapter(medicineNamelist, sellPriceList, medicineUidList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                System.out.println("Failed Message ------------"+error.getMessage());
            }
        });
    }

    private void setMedicineAdapter(ArrayList<String> list, ArrayList<String> sellPriceList, ArrayList<String> medicineUidList) {
        medicineNameadapter = new ArrayAdapter<>(SellMedicine.this, android.R.layout.simple_spinner_dropdown_item, list);
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
                            StockMedicineDataHolder data = snapshot.getValue(StockMedicineDataHolder.class);
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

                String sellPrice = sellPriceList.get(position);

                sellPriceEditText.getEditText().setText(sellPrice);

                double price = Double.parseDouble(sellPriceEditText.getEditText().getText().toString());

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        if (!sellPriceEditText.getEditText().getText().toString().equals("") && !sellQuantityEditText.getEditText().getText().toString().equals("")){

                            double temp1 = Double.parseDouble(sellQuantityEditText.getEditText().getText().toString());
                            double res= price*temp1;
                            //int due = res - paidPrice;

                            totalPrice.setText(String.valueOf(res));
                            //sellDueAmount.setText(String.valueOf(due));

                            TextWatcher textWatcher1 = new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if (!totalPrice.getText().toString().equals("") && !sellPaidAmount.getEditText().getText().toString().equals("")) {

                                        double temp2 = Double.parseDouble(sellPaidAmount.getEditText().getText().toString());
                                        double result = res - temp2;
                                        //int due = res - paidPrice;
                                        recentDue = result;
                                        sellDueAmount.setText(String.valueOf(result));

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
                            sellPaidAmount.getEditText().addTextChangedListener(textWatcher1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!editable.toString().equals("")) {

                            finalMedicineQuantity = recentMedicineQuantity - Double.parseDouble(editable.toString());
                            stockQuantityTextView.setText(String.valueOf(finalMedicineQuantity));

                            System.out.println("RECENT STOCk ----> "+finalMedicineQuantity);

                        } else {

                            System.out.println(" NULL  ");
                            stockQuantityTextView.setText(String.valueOf(recentMedicineQuantity));
                        }
                    }
                };

                sellPriceEditText.getEditText().addTextChangedListener(textWatcher);
                sellQuantityEditText.getEditText().addTextChangedListener(textWatcher);
                //sellPaidAmount.getEditText().addTextChangedListener(textWatcher);
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
                    addAccountDataHolder data = paymentType.getValue(addAccountDataHolder.class);
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

        paymentTypeAdapter = new ArrayAdapter<>(SellMedicine.this, android.R.layout.simple_spinner_dropdown_item,paymentTypeList);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void sellMedicine() {

        customerFinalDue = recentDue + selectedCustomerDueFromDb;

        DatabaseReference selectedCustomerRef = FirebaseDatabase.getInstance()
                .getReference(user.getUid()+"/Medicine/Customer/"+selectedCustomerUid);

        System.out.println("Last uid is -> "+selectedCustomerRef);

        HashMap <String, Object> map = new HashMap<>();
        map.put("total_due",String.valueOf(customerFinalDue));
        selectedCustomerRef.updateChildren(map);

        String customer_name= customerNameSpinner.getSelectedItem().toString();
        String medicine_name= medicineNameSpinner.getSelectedItem().toString();

        String sell_date= sellDateEditText.getText().toString();
        String payment_type= paymentTypeSpinner.getSelectedItem().toString();

        String stock_quantity= stockQuantityTextView.getText().toString();
        String sell_quantity= sellQuantityEditText.getEditText().getText().toString();
        String unit_sell_price= sellPriceEditText.getEditText().getText().toString();
        String total_price= totalPrice.getText().toString();
        String paid_amount= sellPaidAmount.getEditText().getText().toString();
        String due_amount= sellDueAmount.getText().toString();


        String key = db.push().getKey();

        SellMedicineDataHolder obj = new SellMedicineDataHolder(customer_name, medicine_name, sell_date, payment_type, stock_quantity,
                sell_quantity, unit_sell_price, total_price, paid_amount, due_amount);

        FirebaseDatabase addAccount = FirebaseDatabase.getInstance();
        DatabaseReference node = addAccount.getReference(user.getUid());

        node.child("Medicine").child("Sell Medicine").child(key).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                long currentDateAndTimeProvider = System.currentTimeMillis();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                todayDate = dateFormat.format(currentDateAndTimeProvider);

                System.out.println("TODAY's date is --> "+ todayDate);

                String supplierUid = new SupplierDataHolder().getSupplier_uid();
                String transactionUid = new TransactionDataHolder().getUid();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference stockRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Transaction/");

                transaction_Uid = stockRef.push().getKey();

                stockRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Transaction/"+transaction_Uid);

                TransactionDataHolder data = new TransactionDataHolder(transaction_Uid,"Paid amount while sell Medicine", todayDate, paid_amount, payment_type, customer_name );
                stockRef.setValue(data);
            }
        });

        assert user != null;
        dbStock= FirebaseDatabase.getInstance().getReference(user.getUid());
        assert key != null;

        HashMap<String, Object> stockMap = new HashMap<>();
        stockMap.put("stock_quantity", stockQuantityTextView.getText().toString());

        dbStock.child("Medicine").child("Stock Medicine").child(selectedMedicineUid).updateChildren(stockMap);

        // paid amount er edit text clear hole age balance thik moto show korar jonno....

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference selectedAccountRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Accounts/"+selectedAccountUid);
        double accountFinalBalance = recentAccountBalance + Double.parseDouble(sellPaidAmount.getEditText().getText().toString());

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

        Toast.makeText(getApplicationContext(),"Medicine Sell",Toast.LENGTH_LONG).show();

    }
}