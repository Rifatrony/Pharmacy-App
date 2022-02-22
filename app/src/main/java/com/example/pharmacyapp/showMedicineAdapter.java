package com.example.pharmacyapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class showMedicineAdapter extends RecyclerView.Adapter<showMedicineAdapter.MyViewHolder> {

    Context context;
    ArrayList<addMedicineDataHolder> list;

    public showMedicineAdapter(Context context, ArrayList<addMedicineDataHolder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_medicine_sample_layout,parent,false);
        return new showMedicineAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        addMedicineDataHolder data = list.get(position);

        holder.m_name.setText(data.getM_name());
        holder.s_manufacture.setText(data.getS_manufacture());
        holder.box_pattern.setText(data.getBox_pattern());
        holder.m_category.setText(data.getM_category());
        holder.m_type.setText(data.getM_type());
        holder.m_unit.setText(data.getM_unit());
        holder.s_genericName.setText(data.getS_genericName());
        holder.sell_price.setText(data.getSell_price());
        holder.manufacture_price.setText(data.getManufacture_price());
        holder.shelf_no.setText(data.getShelf_no());

        holder.medicineLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.view_medicine_dialog_content);

                EditText m_name = dialog.findViewById(R.id.mnameid);
                EditText manu_name = dialog.findViewById(R.id.manunameid);
                EditText box_pattern = dialog.findViewById(R.id.boxpid);
                EditText m_category = dialog.findViewById(R.id.mcategoryid);
                EditText m_type = dialog.findViewById(R.id.mtypeid);
                EditText m_unit = dialog.findViewById(R.id.munitid);
                EditText g_name = dialog.findViewById(R.id.gnameid);
                EditText sell_price = dialog.findViewById(R.id.sellpid);
                EditText manu_price = dialog.findViewById(R.id.manupid);
                EditText shelf_no = dialog.findViewById(R.id.shelfnoid);

                Button submit = dialog.findViewById(R.id.updateid);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                m_name.setText(data.getM_name());
                manu_name.setText(data.getS_manufacture());
                box_pattern.setText(data.getBox_pattern());
                m_category.setText(data.getM_category());
                m_type.setText(data.getM_type());
                m_unit.setText(data.getM_unit());
                g_name.setText(data.getS_genericName());
                sell_price.setText(data.getSell_price());
                manu_price.setText(data.getManufacture_price());
                shelf_no.setText(data.getShelf_no());

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("m_name",m_name.getText().toString());
                        map.put("s_manufacture",manu_name.getText().toString());
                        map.put("box_pattern",box_pattern.getText().toString());
                        map.put("m_category",m_category.getText().toString());
                        map.put("m_type",m_type.getText().toString());
                        map.put("m_unit",m_unit.getText().toString());
                        map.put("s_genericName",g_name.getText().toString());
                        map.put("sell_price",sell_price.getText().toString());
                        map.put("manufacture_price",manu_price.getText().toString());
                        map.put("shelf_no",shelf_no.getText().toString());

                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine List")
                                .child(data.getUid()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(holder.m_name.getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                    }
                                });

                    }
                });

                dialog.show();
            }
        });

        holder.medicineLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Medicine")
                        .setMessage("Do you want to delete this")
                        .setIcon(R.drawable.question)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine List")
                                        .child(data.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.m_name.getContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();

                                    }
                                });

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

    public void filterList(ArrayList<addMedicineDataHolder> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView m_name,box_pattern,m_category,m_unit,sell_price,
                manufacture_price,shelf_no,s_manufacture,
                s_genericName,m_type;

        LinearLayout medicineLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            m_name = itemView.findViewById(R.id.medicineTextViewId);
            s_manufacture = itemView.findViewById(R.id.manufactureTextViewId);
            box_pattern = itemView.findViewById(R.id.boxPattternTextViewId);
            m_category = itemView.findViewById(R.id.medicineCategoryTextViewId);
            m_type = itemView.findViewById(R.id.medicineTypeTextViewId);
            m_unit = itemView.findViewById(R.id.medicineUnitTextViewId);
            s_genericName = itemView.findViewById(R.id.genericNameTextViewId);
            sell_price = itemView.findViewById(R.id.sellPriceTextViewId);
            manufacture_price = itemView.findViewById(R.id.manufacturePriceTextViewId);
            shelf_no = itemView.findViewById(R.id.shelfNoTextViewId);

            medicineLinearLayout = itemView.findViewById(R.id.medicineLinearLayout);

        }
    }
}
