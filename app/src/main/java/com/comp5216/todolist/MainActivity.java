package com.comp5216.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddItemDialog.AddItemDialogListener, EditItemDialog.EditItemDialogListener {

    private ArrayList<ToDoItem> items;
    private ToDoListViewAdapter adapter;
    private ListView listView;
    private ToDoItemDB db;
    private ToDoItemDao dao;
    private String add_item_new_title; // Title received from the add item dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();

        adapter = new ToDoListViewAdapter(this, items);
        listView = findViewById(R.id.ListView_to_do);
        listView.setAdapter(adapter);

        db = ToDoItemDB.getDatabase(this.getApplication().getApplicationContext());
        dao = db.toDoItemDao();
        adapter.setDao(dao);
        readItemsFromDatabase();
    }

    public void onAddItemClick(View view) {
        showAddItemDialog();
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
    private void saveItemToDatabase(final ToDoItem item) {
        //Use asynchronous task to run query on the background to avoid locking UI
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.insert(item);
                Log.i("SQLite saved item", item.getToDoItemTitle());
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

    public void showAddItemDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new AddItemDialog();
        dialog.show(getSupportFragmentManager(), "AddItemDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void sendTitleAndDate(String title) {

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void sendTitle(final String title) {
        if (title != null && title.length() > 0) {
            ToDoItem new_todo_item = new ToDoItem(title);
            saveItemToDatabase(new_todo_item);
            adapter.addToDoItem(new_todo_item);
        }
    }
}