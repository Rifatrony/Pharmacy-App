package com.example.pharmacyapp.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyapp.Model.CustomerModel;
import com.example.pharmacyapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    Context context;
    ArrayList<CustomerModel> list;

    public CustomerAdapter(Context context, ArrayList<CustomerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.customer_sample_layout,parent,false);
        return new CustomerAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CustomerModel data = list.get(position);
        holder.customer_name.setText(data.getCustomer_name());
        holder.customer_contact.setText(data.getCustomer_contact());
        holder.customer_address.setText(data.getCustomer_address());

        holder.customerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.customer_edit_sample_layout);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                EditText customerNameEditText = dialog.findViewById(R.id.updateCustomerName);
                EditText customerContactEditText = dialog.findViewById(R.id.updateCustomerContact);
                EditText customerAddressEditText = dialog.findViewById(R.id.updateCustomerAddress);

                Button update = dialog.findViewById(R.id.updateCustomerButton);

                customerNameEditText.setText(data.getCustomer_name());
                customerAddressEditText.setText(data.getCustomer_address());
                customerContactEditText.setText(data.getCustomer_contact());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("customer_name",customerNameEditText.getText().toString());
                        map.put("customer_address",customerAddressEditText.getText().toString());
                        map.put("customer_contact",customerContactEditText.getText().toString());

                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Customer")
                                .child(data.getUid()).updateChildren(map);

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.customerLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Customer")
                        .setMessage("Do you want to delete this")
                        .setIcon(R.drawable.question)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Customer")
                                        .child(data.getUid()).removeValue();

                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                builder.show();


                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<CustomerModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView customer_name, customer_contact, customer_address;

        LinearLayout customerLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            customer_name = itemView.findViewById(R.id.customerNameTextView);
            customer_contact = itemView.findViewById(R.id.contactTextView);
            customer_address = itemView.findViewById(R.id.addressTextView);

            customerLinearLayout = itemView.findViewById(R.id.customerLinearLayout);

        }
    }

}
