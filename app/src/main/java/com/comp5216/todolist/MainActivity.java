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
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddItemDialog.AddItemDialogListener {

    private ArrayList<ToDoItem> items;
    private ToDoListViewAdapter adapter;
    private ListView listView;
    private ToDoItemDB db;
    private ToDoItemDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();

        db = ToDoItemDB.getDatabase(this.getApplication().getApplicationContext());
        dao = db.toDoItemDao();
        readItemsFromDatabase();

        adapter = new ToDoListViewAdapter(this, items);
        listView = findViewById(R.id.ListView_to_do);
        listView.setAdapter(adapter);
    }

    public void onAddItemClick(View view) {
        showAddItemDialog();
    }

    /**
     * Saves a to-do item to the database
     *
     * @param item
     */
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

    /**
     * Reads all to-do items from the database
     */
    @SuppressLint("StaticFieldLeak")
    private void readItemsFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    List<ToDoItem> itemsFromDB = dao.listAll(); //read items from database
                    items.clear();
                    if (itemsFromDB != null && itemsFromDB.size() > 0) {
                        for (ToDoItem item : itemsFromDB) {
                            items.add(item);
                            Log.i("SQLite read item", "ID: " + item.getToDoItemID() + " Name: " + item.getToDoItemTitle());
                        }
                    }
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e("readItemsFromDatabase", Arrays.toString(ex.getStackTrace()));
        }
    }

    /**
     * Spawn the Add Item View Dialog
     */
    public void showAddItemDialog() {
        DialogFragment dialog = new AddItemDialog();
        dialog.show(getSupportFragmentManager(), "AddItemDialog");
    }

    /**
     * Spawn the Delete item Alert Dialog
     *
     * @param item
     * @param position
     */
    public void showDeleteAlertDialog(ToDoItem item, final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete to do item")
                .setMessage("Are you sure you want to delete this item with title " + item.getToDoItemTitle() + "?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteToDoItemRunner(adapter, dao, adapter.getListData(), position).execute();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Spawn the Cancel edit alert dialog
     * Spawned as an alert when an edit is to be discarded
     * If edit is not discarded, go back to already spawned edit item dialog
     *
     * @param item
     * @param position
     * @param text
     */
    public void showCancelEditAlertDialog(final ToDoItem item, final int position, final String text) {
        final MainActivity current = this;
        new AlertDialog.Builder(this)
                .setTitle("Cancel Edit")
                .setMessage("Are you sure you want to cancel this edit?")
                .setPositiveButton(R.string.yes, null)
                .setNegativeButton(R.string.go_back, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        current.showEditItemDialog(item, position, text);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    /**
     * Spawns the edit item view dialog
     * If edit dialog is to be closed, it will spawn the cancel alert dialog
     *
     * @param item
     * @param position
     * @param text
     */
    public void showEditItemDialog(final ToDoItem item, final int position, String text) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View alertView = layoutInflater.inflate(R.layout.layout_edit_item, null);
        final EditText editTtemText = alertView.findViewById(R.id.EditText_edit_item);
        final String currentText = text.equals("") ? item.getToDoItemTitle() : text;

        // Set edit text cursor to be after the title of chosen to-do item
        editTtemText.setText(currentText);
        editTtemText.setSelection(editTtemText.getText().length());

        final MainActivity current = this;
        new AlertDialog.Builder(this)
                .setTitle("Edit Item")
                .setView(alertView)
                .setPositiveButton(R.string.save_text, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editTtemText.getText().toString().equals(currentText)) {
                            item.setToDoItemTitle(editTtemText.getText().toString());
                            new UpdateToDoItemRunner(adapter, dao, adapter.getListData(), position).execute();
                        }
                    }
                })
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

    /**
     * Get title from Add Item dialog to add into database
     *
     * @param title
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void sendTitle(final String title) {
        if (title != null && title.length() > 0) {
            ToDoItem new_todo_item = new ToDoItem(title);
            saveItemToDatabase(new_todo_item);
            readItemsFromDatabase();
        }
    }
}