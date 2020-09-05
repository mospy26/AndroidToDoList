package com.comp5216.todolist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

    public ToDoListViewAdapter(Context context, ArrayList<ToDoItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
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

            holder.to_do_title = (EditText) convertView.findViewById(R.id.TextView_to_do_title);
            holder.to_do_date = (TextView) convertView.findViewById(R.id.TextView_to_do_date);

//            final EditText text = holder.to_do_title;
//            text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View view, boolean b) {
//                    Log.i("HAHAHAHAHHHHHHHHHAAHAHHAHAHAHAHH", view.toString());
//                    Editable new_title = text.getText();
//                    listData.get(position).setToDoItemTitle(new_title.toString());
//                    dao.modify(listData.get(position));
//                    Log.i("HAHAHAHAHHHHHHHHHAAHAHHAHAHAHAHH CHAnged", new_title.toString());
//                }
//            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.to_do_title.setText(listData.get(position).getToDoItemTitle());
        holder.to_do_date.setText(listData.get(position).getToDoItemCreatedDateString());

        return convertView;
    }

    static class ViewHolder {
        EditText to_do_title;
        TextView to_do_date;
    }
}