package com.example.todoapplication;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder> {
    Context context;
    Dialog dialog;
    ArrayList<Todo_Model> allTodos;
    Button actionButton;
    DB_Helper db_helper;
    EditText ed_name, ed_number, ed_city;
    public TodoRecyclerViewAdapter(@NonNull Context context, ArrayList<Todo_Model> allTodos) {
        this.context = context;
        this.allTodos = allTodos;
    }

    @NonNull
    @Override
    public TodoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.single_todo,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.tv_name.setText(allTodos.get(position).getName());
        holder.tv_number.setText(allTodos.get(position).getNumber());
        holder.tv_city.setText(allTodos.get(position).getCity());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_helper = new DB_Helper(context);
                boolean isDeleteDate = db_helper.deleteData(allTodos.get(position).getId());
                if(isDeleteDate == true){
                    allTodos.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    Toast.makeText(context, "<TODO Has Been Deleted>", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "TODO Not Deleted>", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_helper = new DB_Helper(context);

                dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_update_popup);

                actionButton = dialog.findViewById(R.id.actionButton);
                ed_name = dialog.findViewById(R.id.ed_name);
                ed_number = dialog.findViewById(R.id.ed_number);
                ed_city = dialog.findViewById(R.id.ed_city);


                ed_name.setText(allTodos.get(holder.getAdapterPosition()).getName());
                ed_number.setText(allTodos.get(holder.getAdapterPosition()).getNumber());
                ed_city.setText(allTodos.get(holder.getAdapterPosition()).getCity());

                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean isUpdateData =
                                db_helper.updateData(
                                        allTodos.get(holder.getAdapterPosition()).getId(),
                                        ed_name.getText().toString(),
                                        ed_number.getText().toString(),
                                        ed_city.getText().toString());

                        if (isUpdateData){
                            allTodos.set(holder.getAdapterPosition(),new Todo_Model(allTodos.get(holder.getAdapterPosition()).getId(),ed_name.getText().toString(),ed_number.getText().toString(),ed_city.getText().toString()));

                            notifyDataSetChanged();
                            notifyItemChanged(holder.getAdapterPosition());

                            Log.d("update1",allTodos.get(position).getNumber());
                            Toast.makeText(context, "<TODO Updated>", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(context, "<TODO Not Updated>", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                actionButton.setText("Update");
                dialog.show();
            }
        });
        Log.d("update2",allTodos.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return allTodos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_number, tv_city;
        CardView cardView;
        Button btn_edit, btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv_city = itemView.findViewById(R.id.tv_city);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}