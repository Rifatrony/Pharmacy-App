package com.example.pharmacyapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class showExpireSoonAdapter extends FirebaseRecyclerAdapter<StockMedicineDataHolder, showExpireSoonAdapter.myViewHolder> {

    public showExpireSoonAdapter(@NonNull FirebaseRecyclerOptions<StockMedicineDataHolder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final StockMedicineDataHolder model) {
        holder.medicineName.setText(model.getMedicineName());
        holder.stock_quantity.setText(model.getStock_quantity());
        holder.expireDate.setText(model.getExpireDate());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expire_soon_medicine_sample_layout,parent,false);
        return new showExpireSoonAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView medicineName,stock_quantity,expireDate;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            medicineName = itemView.findViewById(R.id.medicineTextViewId);
            stock_quantity = itemView.findViewById(R.id.stockMedicineQuantityTextViewId);
            expireDate = itemView.findViewById(R.id.expireDateTextViewId);
        }
    }

}
