package com.comp5216.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Date;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ToDoItem> items;
    private ToDoListViewAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        long now = System.currentTimeMillis();
        items.add(new ToDoItem("Helloworld", new Date(now), new Date(now)));

        adapter = new ToDoListViewAdapter(this, items);
        listView = (ListView) findViewById(R.id.ListView_to_do);
        listView.setAdapter(adapter);
    }
}