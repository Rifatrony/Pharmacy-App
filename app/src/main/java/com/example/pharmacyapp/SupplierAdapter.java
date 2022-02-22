package com.example.pharmacyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyapp.databinding.SupplierDemoViewholderBinding;

import java.util.List;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder>{

    Context context;
    List<SupplierDataHolder> list;

    public SupplierAdapter(Context context, List<SupplierDataHolder> list) {
        this.context = context;
        this.list = list;
    }

    public SupplierAdapter() {
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.supplier_demo_viewholder, parent, false);
        return new SupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierViewHolder holder, int position) {

        if (list.size() > 0) {

            SupplierDataHolder data = list.get(position);

            holder.binding.supplierName.setText(data.getSupplierName());
            holder.binding.supplierDue.setText(data.getTotal_due());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, SupplierEditActivity.class);
                    intent.putExtra("uid", data.getSupplier_uid());
                    context.startActivity(intent);


                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SupplierViewHolder extends RecyclerView.ViewHolder {

        SupplierDemoViewholderBinding binding;

        public SupplierViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SupplierDemoViewholderBinding.bind(itemView);
        }
    }


}
