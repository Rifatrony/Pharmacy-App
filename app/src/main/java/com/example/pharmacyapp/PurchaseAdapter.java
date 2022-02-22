package com.example.pharmacyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyViewHolder> {


    Context context;
    ArrayList<purchaseMedicineDataHolder> list;

    public PurchaseAdapter(Context context, ArrayList<purchaseMedicineDataHolder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.purchase_details_sample_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        purchaseMedicineDataHolder data = list.get(position);

        holder.supplierNameTextView.setText(data.getS_supplier_name());
        holder.medicineNameTextView.setText(data.getS_medicine_name());
        holder.manufactureNameTextView.setText(data.getS_manufacture());
        holder.quantityTextView.setText(data.getQuantity());
        holder.buyDateTextView.setText(data.getBuy_date());
        holder.expireDateTextView.setText(data.getExpire_date());
        holder.totalPriceTextView.setText(data.getTotal_price());

        holder.purchaseDetailsLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Purchase")
                        .setMessage("Do you want to delete this")
                        .setIcon(R.drawable.question)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Purchase Medicine")
                                        .child(data.getPurchase_Uid()).removeValue();


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

    public void filterList(ArrayList<purchaseMedicineDataHolder> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView supplierNameTextView, medicineNameTextView,manufactureNameTextView, quantityTextView,buyDateTextView, expireDateTextView, totalPriceTextView;

        LinearLayout purchaseDetailsLinearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            supplierNameTextView = itemView.findViewById(R.id.supplierNameTextView);
            medicineNameTextView = itemView.findViewById(R.id.purchaseMedicineNameTextView);
            manufactureNameTextView = itemView.findViewById(R.id.manufactureNameTextView);
            quantityTextView = itemView.findViewById(R.id.purchaseQuantityTextView);
            buyDateTextView = itemView.findViewById(R.id.buyDateTextView);
            expireDateTextView = itemView.findViewById(R.id.expireDateTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);

            purchaseDetailsLinearLayout = itemView.findViewById(R.id.purchaseDetailsLinearLayout);

        }
    }

}
