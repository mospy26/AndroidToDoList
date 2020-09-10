package com.comp5216.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements AddItemDialog.AddItemDialogListener {

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

    public void showDeleteAlertDialog(ToDoItem item, final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete to do item")
                .setMessage("Are you sure you want to delete this item with title " + item.getToDoItemTitle() + "?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteToDoItemRunner(adapter, dao, adapter.getListData(), position).execute();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void showCancelEditAlertDialog(final ToDoItem item, final int position, final String text) {
        final MainActivity current = this;
        new AlertDialog.Builder(this)
                .setTitle("Cancel Edit")
                .setMessage("Are you sure you want to cancel this edit?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        current.showEditItemDialog(item, position, text);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void showEditItemDialog(final ToDoItem item, final int position, String text) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View alertView = layoutInflater.inflate(R.layout.layout_edit_item, null);
        final EditText editTtemText = alertView.findViewById(R.id.EditText_edit_item);
        final String currentText = text.equals("") ? item.getToDoItemTitle() : text;
        editTtemText.setText(currentText);
        editTtemText.setSelection(editTtemText.getText().length());
        final MainActivity current = this;
        new AlertDialog.Builder(this)
                .setTitle("Edit Item")
                .setView(alertView)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editTtemText.getText().toString().equals(currentText)) {
                            item.setToDoItemTitle(editTtemText.getText().toString());
                            new UpdateToDoItemRunner(adapter, dao, adapter.getListData(), position).execute();
                        }
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String currentText = editTtemText.getText().toString();
                        current.showCancelEditAlertDialog(item, position, currentText);
                    }
                })
                .setIcon(android.R.drawable.ic_menu_edit)
                .show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

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