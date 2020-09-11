package com.comp5216.todolist;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * Custom AsyncTask runner that updates/edits a to-do item with callback events back to MainActivity
 *
 * @author Mustafa
 * @version 1.0
 */
public class UpdateToDoItemRunner extends AsyncTask<Void, Void, Boolean> {

    private final BaseAdapter baseAdapter;
    private final ToDoItemDao dao;
    private final List<ToDoItem> listData;
    private final int position;

    public UpdateToDoItemRunner(BaseAdapter baseAdapter, ToDoItemDao dao, List<ToDoItem> listData, int position) {
        this.baseAdapter = baseAdapter;
        this.dao = dao;
        this.listData = listData;
        this.position = position;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        dao.update(listData.get(position));
        Log.i("SQLite updated item", listData.get(position).getToDoItemTitle());
        return true;
    }

    /**
     * Callback to MainActivity to re sort items in to-do list inventory
     *
     * @param result
     */
    protected void onPostExecute(Boolean result) {
        ((ToDoListViewAdapter) baseAdapter).notifySortedDataSetChanged();
    }
}
