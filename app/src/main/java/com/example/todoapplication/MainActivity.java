package com.example.todoapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Dialog dialog;
    DB_Helper db_helper;
    Todo_Model todo_model;
    TodoRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    ArrayList<Todo_Model> allTodos;
    TextView dataNotFound;
    Button actionButton;
    EditText ed_name, ed_number, ed_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db_helper = new DB_Helper(MainActivity.this);
        recyclerView = findViewById(R.id.recyclerView);
        dataNotFound = findViewById(R.id.dataNotFound);
        
        refreshTodos();
    }

    public void add_todos(View view) {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_update_popup);

        actionButton = dialog.findViewById(R.id.actionButton);
        ed_name = dialog.findViewById(R.id.ed_name);
        ed_number = dialog.findViewById(R.id.ed_number);
        ed_city = dialog.findViewById(R.id.ed_city);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_name.length() == 0) {
                    ed_name.setError("This field is required");
                }
                else if (ed_number.length() == 0) {
                    ed_number.setError("This field is required");
                }
                else if (ed_city.length() == 0) {
                    ed_city.setError("This field is required");
                }else{
                    boolean isInsertData = db_helper.insertData(ed_name.getText().toString(),ed_number.getText().toString(),ed_city.getText().toString());
                    if(isInsertData == true){
                        refreshTodos();
                        Toast.makeText(MainActivity.this, "<TODO Inserted>", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "<TODO Not Inserted>", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void todo(){
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    public void refreshTodos(){
        allTodos = db_helper.selectAll();

        if(allTodos.isEmpty()){
            dataNotFound.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            dataNotFound.setVisibility(View.GONE);
            recyclerViewAdapter = new TodoRecyclerViewAdapter(MainActivity.this,allTodos);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }
}