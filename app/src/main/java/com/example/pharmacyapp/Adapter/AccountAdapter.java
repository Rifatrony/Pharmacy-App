package com.example.pharmacyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyapp.R;
import com.example.pharmacyapp.addAccountDataHolder;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {

    Context context;
    ArrayList<addAccountDataHolder> list;
    //ArrayList<addAccountDataHolder> newSearchList;

    public AccountAdapter(Context context, ArrayList<addAccountDataHolder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.account_sample_layout,parent,false);
        return new AccountAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        addAccountDataHolder data = list.get(position);
        holder.bankName.setText(data.getBank_name());
        holder.accountName.setText(data.getAccount_name());
        holder.accountNumber.setText(data.getAccount_number());
        holder.openingBalance.setText(data.getOpening_balance());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView bankName, accountName, accountNumber, openingBalance;
        ImageView edit, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bankName = itemView.findViewById(R.id.bankNameTextView);
            accountName = itemView.findViewById(R.id.accountNameTextView);
            accountNumber = itemView.findViewById(R.id.accountNumberTextView);
            openingBalance = itemView.findViewById(R.id.openingBalanceTextView);
            //edit = itemView.findViewById(R.id.editAccountId);
            //delete = itemView.findViewById(R.id.deleteAccountId);

        }
    }

}
