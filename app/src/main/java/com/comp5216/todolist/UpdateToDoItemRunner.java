package com.comp5216.todolist;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import java.util.List;

public class UpdateToDoItemRunner extends AsyncTask<Void, Void, Boolean> {

    private BaseAdapter baseAdapter;
    private ToDoItemDao dao;
    private List<ToDoItem> listData;
    private int position;

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

    protected void onPostExecute(Boolean result) {
        ((ToDoListViewAdapter) baseAdapter).notifySortedDataSetChanged();
    }
}
