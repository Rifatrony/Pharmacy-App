package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmacyapp.databinding.ActivityCustomerEditBinding;
import com.example.pharmacyapp.databinding.ActivitySupplierEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerEditActivity extends AppCompatActivity {

    ActivityCustomerEditBinding binding;
    EditText customerNameEditText;
    TextView pendingTextView;

    Spinner paymentTypeSpinner;
    TextView accountBalance;

    String uid;
    String writtenName;
    String customerPendingDue;
    String paidAmount, todayDate,transaction_Uid;
    String SupplierName;
    DatabaseReference dbCustomer;
    CustomerDataHolder data;
    double dueFromDb = 0;
    double finalDue = 0;
    boolean isValid = true;
    double mainBalance = 0;
    FirebaseUser user;
    double updatedBalance = 0;
    DatabaseReference balanceRef,dbAccount, dbTransaction;

    String selectedAccountUid = null;

    ArrayList<String> paymentTypeList;
    ArrayList<String> accountBalanceList;
    ArrayList<String> accountUidList;
    ArrayAdapter<String> paymentTypeAdapter;
    double recentAccountBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.setTitle("Customer Due");

        //Add back Button on tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        paymentTypeSpinner = findViewById(R.id.spinnerAccountId);
        accountBalance = findViewById(R.id.customerAccountBalanceId);

        uid = getIntent().getStringExtra("uid");


        customerNameEditText = findViewById(R.id.customerName);
        pendingTextView = findViewById(R.id.customerPendingDue);

        //setBalanceFromDb();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        dbAccount = FirebaseDatabase.getInstance().getReference(user.getUid())
                .child("Medicine").child("Accounts");

        paymentTypeList = new ArrayList<>();
        accountUidList = new ArrayList<>();
        accountBalanceList = new ArrayList<>();


        //Fetch supplier pending due

        if (uid != null) {

            user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            dbCustomer = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/"+"/Customer/"+uid);

            dbCustomer.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    data = snapshot.getValue(CustomerDataHolder.class);

                    if (data != null) {

                        customerNameEditText.setText(data.getCustomer_name());
                        pendingTextView.setText(data.getTotal_due());

                        /*binding.customerName.setText(data.getCustomer_name());
                        binding.customerPendingDue.setText(data.getTotal_due());*/
                        dueFromDb = Double.parseDouble(data.getTotal_due());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        binding.customerTotalDue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {

                    finalDue = dueFromDb - Double.parseDouble(s.toString());

                    System.out.println("FINAL DUE --> "+finalDue);

                    if (finalDue == 0) {

                        binding.customerPendingDue.setText(String.valueOf(finalDue));

                    }
                    else if (finalDue < 0) {

                        binding.customerPendingDue.setText("Invalid");
                        isValid = false;
                    }
                    else {

                        binding.customerPendingDue.setText(String.valueOf(finalDue));
                    }
                }
                else {
                    binding.customerPendingDue.setText(String.valueOf(dueFromDb));
                    System.out.println("Due is------------->"+ dueFromDb);
                }
            }
        });

        fetchPaymentTypeData();
    }


    //Back button on top navbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
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

    private void setAccountAdapter(ArrayList<String> paymentTypeList , ArrayList<String> accountBalanceList, ArrayList<String> accountUidList) {

        paymentTypeAdapter = new ArrayAdapter<>(CustomerEditActivity.this, android.R.layout.simple_spinner_dropdown_item,paymentTypeList);
        paymentTypeSpinner.setAdapter(paymentTypeAdapter);

        paymentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedAccountUid = accountUidList.get(i);
                String parAccountBalance = accountBalanceList.get(i);
                //selectedAccountUid = parAccountBalance;
                recentAccountBalance = Double.parseDouble(parAccountBalance);

                accountBalance.setText(String.valueOf(recentAccountBalance));
                //singleAccountBalance = accountBalance;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void Submit(View view) {

        writtenName = binding.customerName.getText().toString().trim();
        paidAmount = binding.customerTotalDue.getText().toString().trim();
        customerPendingDue = binding.customerPendingDue.getText().toString().trim();


        if (paidAmount.equals("0")) {
            Toast.makeText(CustomerEditActivity.this, "Please type a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (paidAmount.isEmpty() || customerPendingDue.isEmpty() ) {
            Toast.makeText(CustomerEditActivity.this, "Field is blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Double.parseDouble(paidAmount) > recentAccountBalance) {
            Toast.makeText(CustomerEditActivity.this, "You don't have sufficient balance", Toast.LENGTH_SHORT).show();
            return;
        }

        if(Double.parseDouble(paidAmount) < 0) {
            Toast.makeText(CustomerEditActivity.this, "Please type a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (writtenName.isEmpty()) {
            writtenName = data.getCustomer_name();
        }

        updatedBalance = recentAccountBalance + Double.parseDouble(paidAmount);

        HashMap<String, Object> balanceMap = new HashMap<>();
        balanceMap.put("opening_balance", String.valueOf(updatedBalance));
        dbAccount.child(selectedAccountUid).updateChildren(balanceMap);



        Map<String, Object> map = new HashMap<>();
        map.put("customer_name", writtenName);
        map.put("total_due", customerPendingDue);

        dbCustomer.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                binding.customerTotalDue.setText("");
                Toast.makeText(CustomerEditActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CustomerEditActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


        long currentDateAndTimeProvider = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        todayDate = dateFormat.format(currentDateAndTimeProvider);

        String payment_type= paymentTypeSpinner.getSelectedItem().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        dbTransaction = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Transaction/");

        transaction_Uid = dbTransaction.push().getKey();

        dbTransaction = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Transaction/"+transaction_Uid);

        TransactionDataHolder data = new TransactionDataHolder(transaction_Uid,"Take Due from Customer", todayDate, paidAmount, payment_type, writtenName);
        dbTransaction.setValue(data);


    }

    public void DeleteSupplier(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;

        dbCustomer = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/"+"/Customer/"+uid);
        dbCustomer.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(CustomerEditActivity.this, "Supplier Deleted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CustomerEditActivity.this, DueCustomer.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CustomerEditActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

}