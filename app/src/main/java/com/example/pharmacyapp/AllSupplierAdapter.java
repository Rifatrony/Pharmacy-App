package com.example.pharmacyapp;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllSupplierAdapter extends RecyclerView.Adapter<AllSupplierAdapter.MyViewHolder> {


    Context context;
    ArrayList<SupplierDataHolder> list;

    public AllSupplierAdapter(Context context, ArrayList<SupplierDataHolder> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_all_supplier_sample_layout,parent,false);
        return new AllSupplierAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SupplierDataHolder data = list.get(position);
        holder.supplerName.setText(data.getSupplierName());
        holder.supplierContact.setText(data.getSupplier_contact());

       holder.allSupplierLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.supplier_edit_sample_layout);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                EditText supplierNameEditText = dialog.findViewById(R.id.updateSupplierName);
                EditText supplierContactEditText = dialog.findViewById(R.id.updateSupplierContact);

                Button update = dialog.findViewById(R.id.updateSupplierButton);

                supplierNameEditText.setText(data.getSupplierName());
                supplierContactEditText.setText(data.getSupplier_contact());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("supplierName",supplierNameEditText.getText().toString());
                        map.put("supplier_contact",supplierContactEditText.getText().toString());

                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Supplier List")
                                .child(data.getSupplier_uid()).updateChildren(map);
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }

        });


        holder.allSupplierLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Supplier")
                        .setMessage("Do you want to delete this")
                        .setIcon(R.drawable.question)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Supplier List")
                                        .child(data.getSupplier_uid()).removeValue();


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

    public void filterList(ArrayList<SupplierDataHolder> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView supplerName, supplierContact;
        LinearLayout allSupplierLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            supplerName = itemView.findViewById(R.id.supplierName);
            supplierContact = itemView.findViewById(R.id.supplierContact);
            allSupplierLinearLayout = itemView.findViewById(R.id.allSupplierLinearLayout);


        }
    }


}
