package com.example.pharmacyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyapp.Model.TransactionModel;
import com.example.pharmacyapp.R;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder>{

    Context context;
    ArrayList<TransactionModel> list;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.transaction_sample_layout,parent,false);
        return new TransactionAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TransactionModel data = list.get(position);

        holder.bank_name.setText(data.getBank());
        holder.date.setText(data.getDate());
        holder.amount.setText(data.getAmount());
        holder.reason.setText(data.getReason());
        holder.name.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<TransactionModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView bank_name, date, amount, reason, name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bank_name = itemView.findViewById(R.id.transactionBank);
            date = itemView.findViewById(R.id.transactionDate);
            amount = itemView.findViewById(R.id.transactionAmount);
            reason = itemView.findViewById(R.id.transactionReason);
            name = itemView.findViewById(R.id.transactionName);


        }
    }

}
