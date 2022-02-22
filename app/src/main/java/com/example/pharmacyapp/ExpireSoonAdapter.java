package com.example.pharmacyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpireSoonAdapter extends RecyclerView.Adapter<ExpireSoonAdapter.MyViewHolder> {

    Context context;
    ArrayList<purchaseMedicineDataHolder> list;

    public ExpireSoonAdapter(Context context, ArrayList<purchaseMedicineDataHolder> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.expire_soon_sample_layout,parent,false);
        return new ExpireSoonAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        purchaseMedicineDataHolder data = list.get(position);
        holder.supplier.setText(data.getS_supplier_name());
        holder.batchId.setText(data.getBatch_id());
        holder.medicine_name.setText(data.getS_medicine_name());
        holder.quantity.setText(data.getQuantity());
        holder.expire_date.setText(data.getExpire_date());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filterList(ArrayList<purchaseMedicineDataHolder> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView supplier, batchId, medicine_name, quantity, expire_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            supplier = itemView.findViewById(R.id.supplierNameTextView);
            batchId = itemView.findViewById(R.id.batchIdTextView);
            medicine_name = itemView.findViewById(R.id.medicineNameTextView);
            quantity = itemView.findViewById(R.id.purchaseQuantityTextView);
            expire_date = itemView.findViewById(R.id.expireDateTextView);
        }
    }

}
