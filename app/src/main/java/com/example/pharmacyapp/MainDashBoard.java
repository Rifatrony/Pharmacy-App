package com.example.pharmacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pharmacyapp.Model.SellMedicineModel;
import com.example.pharmacyapp.Model.AddAccountModel;
import com.example.pharmacyapp.databinding.ActivityMainDashBoardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class MainDashBoard extends AppCompatActivity implements View.OnClickListener {
    LinearLayout medicineLayout,stockLayout,purchaseLayout,
            sellLayout,customerLayout,paymentLayout, noteLayout, expireLayout,
            allReportLayout, supplierLayout, transactionDetails, totalMedicine, totalSell;

    TextView countNumberOfMedicineTextView;


    private AlertDialog.Builder alertDialogBuilder;
    ActivityMainDashBoardBinding binding;
    DatabaseReference totalBalanceRef;
    double total_sale = 0;
    double today_sale = 0;
    double balance = 0;
    int countMedicine = 0;
    HashMap<String, Object> balanceMap;

    String todayDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.setTitle(R.string.dashboard);

        getTotalSaleFromDB();
        getTotalMedicineFromDb();

        setBalanceFromDb();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Add back Button on tool bar
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/


        medicineLayout = findViewById(R.id.medicineLayoutId);
        stockLayout = findViewById(R.id.stockLayoutId);
        purchaseLayout = findViewById(R.id.purchaseLayoutId);
        sellLayout = findViewById(R.id.sellLayoutId);
        customerLayout = findViewById(R.id.customerLayoutId);
        paymentLayout = findViewById(R.id.accountLayoutId);
        noteLayout = findViewById(R.id.noteLayoutId);
        expireLayout = findViewById(R.id.expireSoonLayoutId);
        allReportLayout = findViewById(R.id.allReportLayoutId);
        supplierLayout = findViewById(R.id.supplierLayoutId);
        transactionDetails = findViewById(R.id.transactionDetailsId);
        totalMedicine = findViewById(R.id.totalMedicineLinearLayoutId);
        totalSell = findViewById(R.id.totalSaleLinearLayoutId);

        countNumberOfMedicineTextView = findViewById(R.id.countNumberOfMedicine);


        medicineLayout.setOnClickListener(this);
        stockLayout.setOnClickListener(this);
        purchaseLayout.setOnClickListener(this);
        sellLayout.setOnClickListener(this);
        customerLayout.setOnClickListener(this);
        paymentLayout.setOnClickListener(this);
        noteLayout.setOnClickListener(this);
        expireLayout.setOnClickListener(this);
        allReportLayout.setOnClickListener(this);
        supplierLayout.setOnClickListener(this);
        transactionDetails.setOnClickListener(this);
        totalMedicine.setOnClickListener(this);
        totalSell.setOnClickListener(this);

        totalBalanceRef = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Main Balance");




    }

    private void getTotalMedicineFromDb() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;

        DatabaseReference medicineListRef = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine List");

        medicineListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    countMedicine = (int) snapshot.getChildrenCount();
                    System.out.println("Number of medicine is ======>"+ countMedicine);
                    countNumberOfMedicineTextView.setText(String.valueOf(countMedicine));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setBalanceFromDb() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;

        DatabaseReference totalBalanceRef = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Main Balance");
        DatabaseReference purchaseRef = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Purchase Medicine");
        balanceMap = new HashMap<>();

        DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Accounts");


        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                balance = 0;

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    AddAccountModel data = snapshot1.getValue(AddAccountModel.class);

                    assert data != null;
                    balance += Double.parseDouble(data.getOpening_balance());
                    binding.balance.setText(String.valueOf(balance));

                    HashMap<String, Object> stockMap = new HashMap<>();
                    balanceMap.put("balance", balance);

                    //balanceMap.put("balance",String.valueOf(balance));

                    System.out.println("MAIN BALANCE IS ->"+String.valueOf(balance));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getTotalSaleFromDB() {

        long currentDateAndTimeProvider = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        todayDate = dateFormat.format(currentDateAndTimeProvider);

        System.out.println("TODAY's date is --> "+ todayDate);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        DatabaseReference saleRef = FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Sell Medicine");
        saleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                total_sale = 0;
                today_sale = 0;

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    SellMedicineModel data = snapshot1.getValue(SellMedicineModel.class);

                    if (data != null) {

                        total_sale = total_sale + Double.parseDouble(data.getPaid_amount());
                        binding.totalSaleBalanceId.setText(String.valueOf(total_sale));
                        System.out.println("TOTAl AMOUNT IS ---> "+total_sale);

                        if (todayDate.equals (data.getSell_date())) {

                            today_sale = today_sale + Double.parseDouble(data.getPaid_amount());
                            binding.dailySaleBalanceId.setText(String.valueOf(today_sale));

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

  public void onBackPressed() {

      alertDialogBuilder = new AlertDialog.Builder(MainDashBoard.this);

      //set title
      alertDialogBuilder.setTitle("Confirm Exit");

      //Setting message

      alertDialogBuilder.setMessage("Do you really want to exit ?");

      //set icon

      alertDialogBuilder.setIcon(R.drawable.helpicondelete);

      alertDialogBuilder.setCancelable(false);

      //set positive button
      alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
              //exit
              finish();
          }
      });

      alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {

              dialogInterface.cancel();
          }
      });

      /*alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {

              Toast.makeText(MainDashBoard.this, "You have clicked on cancel button",Toast.LENGTH_SHORT).show();
          }
      });*/

      AlertDialog alertDialog = alertDialogBuilder.create();
      alertDialog.show();

  }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.medicineLayoutId){
            Intent intent = new Intent(this,SubDeshboard.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.stockLayoutId){
            Intent intent = new Intent(this,StockMedicine.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.purchaseLayoutId){
            Intent intent = new Intent(this,PurchaseMedicine.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.sellLayoutId){
            Intent intent = new Intent(this,SellMedicine.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.customerLayoutId){
            Intent intent = new Intent(this,CustomerRadioActivity.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.accountLayoutId){
            Intent intent = new Intent(getApplicationContext(), AccountsActivity.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.noteLayoutId){
            Intent intent = new Intent(this,ShowNoteActivity.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.expireSoonLayoutId){
            Intent intent = new Intent(this,ExpireSoonMedicine.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.allReportLayoutId){
            Intent intent = new Intent(this, All_ReportActivity.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.supplierLayoutId){
            Intent intent = new Intent(this,SupplierRadioActivity.class);
            startActivity(intent);
        }
        if (view.getId()==R.id.transactionDetailsId){
            Intent intent = new Intent(this,TransactionDetails.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.totalMedicineLinearLayoutId){
            Intent intent = new Intent(this,ViewMedicineActivity.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.totalSaleLinearLayoutId){
            Intent intent = new Intent(this,SellDetails.class);
            startActivity(intent);
        }
    }
}