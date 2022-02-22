package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddAccount extends AppCompatActivity {

    TextInputLayout bankNameEditText, accountNameEditText, accountNumberEditText, accountTypeEditText, branchEditText, openingBalanceEditText;
    Button saveAccountButton;

    DatabaseReference db;
    String todayDate, transaction_Uid;

    String uid, reason="this is the reason", date, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        this.setTitle("Add Account");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        bankNameEditText = findViewById(R.id.bankNameId);
        accountNameEditText = findViewById(R.id.accountNameId);
        accountNumberEditText = findViewById(R.id.accountNumberId);
        accountTypeEditText = findViewById(R.id.accountTypeId);
        branchEditText = findViewById(R.id.branchId);
        openingBalanceEditText = findViewById(R.id.openingBalanceId);

        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        date = currentDate;

        saveAccountButton = findViewById(R.id.saveAccountButtonId);

        db = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Accounts");


        saveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAccount();
            }
        });



    }

    private void addAccount() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String bank_name = bankNameEditText.getEditText().getText().toString();
        String account_name = accountNameEditText.getEditText().getText().toString();
        String account_number = accountNumberEditText.getEditText().getText().toString();
        String account_type = accountTypeEditText.getEditText().getText().toString();
        String branch = branchEditText.getEditText().getText().toString();
        String opening_balance = openingBalanceEditText.getEditText().getText().toString();
        uid = db.push().getKey();

       // String key = db.push().getKey();

        if (bank_name.isEmpty()){
            bankNameEditText.getEditText().setError("Required");
            bankNameEditText.getEditText().requestFocus();
            return;
        }

        if (account_name.isEmpty()){
            accountNameEditText.getEditText().setError("Required");
            accountNameEditText.getEditText().requestFocus();
            return;
        }

        if (account_number.isEmpty()){
            accountNumberEditText.getEditText().setError("Required");
            accountNumberEditText.getEditText().requestFocus();
            return;
        }

        if (account_type.isEmpty()){
            accountTypeEditText.getEditText().setError("Required");
            accountTypeEditText.getEditText().requestFocus();
            return;
        }

        if (branch.isEmpty()){
            branchEditText.getEditText().setError("Required");
            branchEditText.getEditText().requestFocus();
            return;
        }

        if (opening_balance.isEmpty()){
            openingBalanceEditText.getEditText().setError("Required");
            openingBalanceEditText.getEditText().requestFocus();
            return;
        }

        addAccountDataHolder obj = new addAccountDataHolder(bank_name, account_name, account_number,account_type, branch , opening_balance, uid);

        FirebaseDatabase addAccount = FirebaseDatabase.getInstance();
        DatabaseReference node = addAccount.getReference(user.getUid());

        node.child("Medicine").child("Accounts").child(uid).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                long currentDateAndTimeProvider = System.currentTimeMillis();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                todayDate = dateFormat.format(currentDateAndTimeProvider);
                //DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Transaction/"+transaction_Uid);

                DatabaseReference dbTransaction = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Transaction/");

                transaction_Uid = dbTransaction.push().getKey();

                dbTransaction = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Medicine/Transaction/"+transaction_Uid);

                TransactionDataHolder data = new TransactionDataHolder(transaction_Uid,"Account Opening Balance", todayDate, opening_balance, bank_name,account_name );
                dbTransaction.setValue(data);

            }
        });

        bankNameEditText.getEditText().setText("");
        accountNameEditText.getEditText().setText("");
        accountNumberEditText.getEditText().setText("");
        accountTypeEditText.getEditText().setText("");
        branchEditText.getEditText().setText("");
        openingBalanceEditText.getEditText().setText("");

        Toast.makeText(getApplicationContext(),"Account Created Successfully",Toast.LENGTH_LONG).show();
        finish();


    }
}