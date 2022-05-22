package com.example.pharmacyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyapp.R;
import com.example.pharmacyapp.Model.StockMedicineModel;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewHolder>{

    Context context;
    ArrayList<StockMedicineModel> list;

    public StockAdapter(Context context, ArrayList<StockMedicineModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sample_stock_layout,parent,false);
        return new StockAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        StockMedicineModel data = list.get(position);
        holder.medicine_name.setText(data.getMedicineName());
        holder.manufacture.setText(data.getManufactureName());
        holder.stock_quantity.setText(data.getStock_quantity());
        holder.sell_price.setText(data.getSell_price());
        holder.medicine_unit.setText(data.getMedicine_unit());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<StockMedicineModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView medicine_name, manufacture, stock_quantity, sell_price, medicine_unit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            medicine_name = itemView.findViewById(R.id.medicineNameTextView);
            manufacture = itemView.findViewById(R.id.manufactureNameTextViewId);
            stock_quantity = itemView.findViewById(R.id.stockTextView);
            sell_price = itemView.findViewById(R.id.sellTextView);
            medicine_unit = itemView.findViewById(R.id.medicineUnitTextView);
        }
    }
}
