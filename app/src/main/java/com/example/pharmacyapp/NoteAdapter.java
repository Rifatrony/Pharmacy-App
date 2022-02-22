package com.example.pharmacyapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    Context context;
    ArrayList<addNoteDataholder> list;

    public NoteAdapter(Context context, ArrayList<addNoteDataholder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.note_sample_layout,parent,false);
        return new NoteAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        addNoteDataholder data = list.get(position);

        holder.title.setText(data.getTitle());
        holder.description.setText(data.getDescription());
        holder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.edit_note_dialog_content);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                EditText titleEditText = dialog.findViewById(R.id.utitle);
                EditText descriptionEditText = dialog.findViewById(R.id.udesc);

                Button saveNote = dialog.findViewById(R.id.updateBtnId);

                titleEditText.setText(data.getTitle());
                descriptionEditText.setText(data.getDescription());

                saveNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("title",titleEditText.getText().toString());
                        map.put("description",descriptionEditText.getText().toString());

                        FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Note Details")
                                .child(data.note_uid).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.title.getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        holder.ll1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Note")
                        .setMessage("Do you want to delete this?")
                        .setIcon(R.drawable.question)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                FirebaseDatabase.getInstance().getReference(user.getUid()).child("Medicine").child("Note Details")
                                        .child(data.note_uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.title.getContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();

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

        TextView title, description;
        LinearLayout ll1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.titleTextViewId);
            description= itemView.findViewById(R.id.descTextViewId);
            ll1 = itemView.findViewById(R.id.linearLayout);

        }
    }
}
