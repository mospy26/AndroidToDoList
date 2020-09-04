package com.comp5216.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ToDoItem> items;
    private ToDoListViewAdapter adapter;
    private ListView listView;
    private EditText new_item;
    private ToDoItemDB db;
    private ToDoItemDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new_item = (EditText) findViewById(R.id.EditText_add_item);
        items = new ArrayList<>();

        adapter = new ToDoListViewAdapter(this, items);
        listView = (ListView) findViewById(R.id.ListView_to_do);
        listView.setAdapter(adapter);

        db = ToDoItemDB.getDatabase(this.getApplication().getApplicationContext());
        dao = db.toDoItemDao();
        readItemsFromDatabase();
    }

    public void onAddItemClick(View view) {
        String title = new_item.getText().toString();

        if (title != null && title.length() > 0) {
            adapter.addToDoItem(new ToDoItem(title));
            new_item.setText("");
            saveItemsToDatabase();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void saveItemsToDatabase() {
        //Use asynchronous task to run query on the background to avoid locking UI
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //delete all items and re-insert
                dao.deleteAll();
                for (ToDoItem todo : items) {
                    dao.insert(todo);
                    Log.i("SQLite saved item", todo.getToDoItemTitle());
                }
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void readItemsFromDatabase() {
        //Use asynchronous task to run query on the background and wait for result
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    //read items from database
                    List<ToDoItem> itemsFromDB = dao.listAll();
                    if (itemsFromDB != null & itemsFromDB.size() > 0) {
                        for (ToDoItem item : itemsFromDB) {
                            items.add(item);
                            Log.i("SQLite read item", "ID: " + item.getToDoItemID() + " Name: " + item.getToDoItemTitle());
                        }
                    }
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e("readItemsFromDatabase", ex.getStackTrace().toString());
        }
    }
}