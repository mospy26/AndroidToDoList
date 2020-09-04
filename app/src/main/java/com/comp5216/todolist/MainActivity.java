package com.comp5216.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Date;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ToDoItem> items;
    private ToDoListViewAdapter adapter;
    private ListView listView;
    private EditText new_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new_item = (EditText) findViewById(R.id.EditText_add_item);
        items = new ArrayList<>();
        items.add(new ToDoItem("Helloworld", new Date(), new Date()));

        adapter = new ToDoListViewAdapter(this, items);
        listView = (ListView) findViewById(R.id.ListView_to_do);
        listView.setAdapter(adapter);
    }

    public void onAddItemClick(View view) {
        String title = new_item.getText().toString();

        if (title != null && title.length() > 0) {
            adapter.addToDoItem(new ToDoItem(title, new Date(), new Date()));
            new_item.setText("");
        }
    }
}