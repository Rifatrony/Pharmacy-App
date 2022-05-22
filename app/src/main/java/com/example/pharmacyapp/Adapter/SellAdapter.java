package com.example.pharmacyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyapp.Model.SellMedicineModel;
import com.example.pharmacyapp.R;

import java.util.ArrayList;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.MyViewHolder> {


    Context context;
    ArrayList<SellMedicineModel> list;

    public SellAdapter(Context context, ArrayList<SellMedicineModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sell_details_sample_layout,parent,false);
        return new SellAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SellMedicineModel data = list.get(position);
        holder.sellMedicineCustomerTextView.setText(data.getCustomer_name());
        holder.sellMedicineNameTextView.setText(data.getMedicine_name());
        holder.sellDateTextView.setText(data.getSell_date());
        holder.sellQuantityTextView.setText(data.getSell_quantity());
        holder.totalSellPriceTextView.setText(data.getTotal_price());
        holder.totalPaidTextView.setText(data.getPaid_amount());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filterList(ArrayList<SellMedicineModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView sellMedicineCustomerTextView, sellMedicineNameTextView,sellDateTextView, sellQuantityTextView, totalSellPriceTextView, totalPaidTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //supplierNameTextView = itemView.findViewById(R.id.supplierNameTextView);
            sellMedicineCustomerTextView = itemView.findViewById(R.id.sellMedicineCustomerTextView);
            sellMedicineNameTextView = itemView.findViewById(R.id.sellMedicineNameTextView);
            sellDateTextView = itemView.findViewById(R.id.sellDateTextView);
            sellQuantityTextView = itemView.findViewById(R.id.sellQuantityTextView);
            totalSellPriceTextView = itemView.findViewById(R.id.totalSellPriceTextView);
            totalPaidTextView = itemView.findViewById(R.id.totalPaidTextView);

        }
    }


}
