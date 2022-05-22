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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacyapp.Model.MedicineCategoryModel;
import com.example.pharmacyapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MedicineCategoryAdapter extends RecyclerView.Adapter<MedicineCategoryAdapter.MyViewHolder>{

    Context context;
    ArrayList<MedicineCategoryModel> list;

    public MedicineCategoryAdapter(Context context, ArrayList<MedicineCategoryModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.medicine_category_sample_layout,parent,false);
        return new MedicineCategoryAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MedicineCategoryModel data = list.get(position);

        holder.medicineCategoryTextView.setText(data.getCategory());

        holder.medicineCategoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.medicine_category_edit_sample_layout);

                EditText medicine_category = dialog.findViewById(R.id.medicineCategoryUpdateTextView);

                Button submit = dialog.findViewById(R.id.medicineCategoryUpdateButton);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                medicine_category.setText(data.getCategory());

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("category",medicine_category.getText().toString());

                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine Category")
                                .child(data.getUid()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(holder.medicineCategoryTextView.getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
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

        holder.medicineCategoryLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Medicine Category")
                        .setMessage("Do you want to delete this")
                        .setIcon(R.drawable.question)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Medicine Category")
                                        .child(data.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.medicineCategoryTextView.getContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();

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

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView medicineCategoryTextView;
        LinearLayout medicineCategoryLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            medicineCategoryTextView = itemView.findViewById(R.id.medicineCategoryId);
            medicineCategoryLinearLayout = itemView.findViewById(R.id.medicineCategoryLinearLayout);
        }
    }

}
