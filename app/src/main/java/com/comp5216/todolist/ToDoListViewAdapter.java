package com.comp5216.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoListViewAdapter extends BaseAdapter {

    private ArrayList<ToDoItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private ToDoItemDao dao;

    public ToDoListViewAdapter(Context context, ArrayList<ToDoItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setDao(ToDoItemDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addToDoItem(ToDoItem item) {
        listData.add(item);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.layout_to_do_list, null);
            holder = new ViewHolder();

            holder.to_do_title = convertView.findViewById(R.id.TextView_to_do_title);
            holder.to_do_date = convertView.findViewById(R.id.TextView_to_do_date);

            // In place editing of to do item
            final BaseAdapter adapter = this;
            holder.to_do_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    final String new_title = holder.to_do_title.getText().toString();
                    if (!hasFocus) {
                        holder.to_do_title.setText(new_title);
                        if (listData.get(position).getToDoItemTitle().equals(new_title)) {
                            return;
                        }
                        listData.get(position).setToDoItemTitle(new_title);
                        new UpdateToDoItemRunner(adapter, dao, listData, position).execute();
                    }
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.to_do_title.setText(listData.get(position).getToDoItemTitle());
        String modifiedDate = listData.get(position).getToDoItemModifiedDateString();
        String displayDate = modifiedDate == null ? listData.get(position).getToDoItemCreatedDateString() : modifiedDate;
        holder.to_do_date.setText(displayDate);

        return convertView;
    }

    static class ViewHolder {
        EditText to_do_title;
        TextView to_do_date;
    }
}