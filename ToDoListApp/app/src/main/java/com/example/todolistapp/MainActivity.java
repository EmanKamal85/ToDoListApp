package com.example.todolistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText addTaskEditText;
    Button addButton;
    ListView taskListView;
    FileHelper fileHelper = new FileHelper();
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTaskEditText = findViewById(R.id.add_task_edit_text);
        addButton = findViewById(R.id.add_button);
        taskListView = findViewById(R.id.task_list);
        itemList = fileHelper.readData(this);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
        taskListView.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addTaskEditText.getText().toString().isEmpty()) {
                    itemList.add(addTaskEditText.getText().toString());
                    fileHelper.writeData(itemList, getApplicationContext());
                    adapter.notifyDataSetChanged();
                    addTaskEditText.setText("");
                }

            }
        });
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setCancelable(false);
                alert.setTitle("Delete task");
                alert.setMessage("Do you want to delete this task?");
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if(!addTaskEditText.getText().toString().isEmpty()) {
                        itemList.remove(position);
                        fileHelper.writeData(itemList, getApplicationContext());
                        adapter.notifyDataSetChanged();
                        // }
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });


    }
}