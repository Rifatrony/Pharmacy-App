package com.example.pharmacyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DueCustomerAdapter extends RecyclerView.Adapter<DueCustomerAdapter.CustomerViewHolder> {

    Context context;
    List<CustomerDataHolder> list;

    public DueCustomerAdapter(Context context, List<CustomerDataHolder> list) {
        this.context = context;
        this.list = list;
    }

    public DueCustomerAdapter() {
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_due_layout, parent, false);
        return new DueCustomerAdapter.CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {

        if (list.size() > 0) {

            CustomerDataHolder data = list.get(position);

            holder.customer_name.setText(data.getCustomer_name());
            holder.customer_due.setText(data.getTotal_due());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, CustomerEditActivity.class);
                    intent.putExtra("uid", data.getUid());
                    context.startActivity(intent);

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder{

        TextView customer_name, customer_due;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            customer_name = itemView.findViewById(R.id.customerName);
            customer_due = itemView.findViewById(R.id.customerDue);

        }
    }

}
