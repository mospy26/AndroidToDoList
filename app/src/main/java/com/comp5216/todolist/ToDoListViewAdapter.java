package com.comp5216.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

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

    public List<ToDoItem> getListData() {
        return listData;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.layout_to_do_list, null);
            holder = new ViewHolder();

            holder.to_do_title = convertView.findViewById(R.id.TextView_to_do_title);
            holder.to_do_date = convertView.findViewById(R.id.TextView_to_do_date);

            holder.to_do_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).showEditItemDialog(listData.get(position), position, "");
                }
            });

            holder.to_do_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).showEditItemDialog(listData.get(position), position, "");
                }
            });

            holder.to_do_date.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((MainActivity) context).showDeleteAlertDialog(listData.get(position), position);
                    return true;
                }
            });

            holder.to_do_title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((MainActivity) context).showDeleteAlertDialog(listData.get(position), position);
                    return true;
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
        TextView to_do_title;
        TextView to_do_date;
    }
}