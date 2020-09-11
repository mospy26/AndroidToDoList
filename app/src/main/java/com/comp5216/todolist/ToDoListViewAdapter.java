package com.comp5216.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This is a custom adapter for displaying to do items and their creation/modification date in a list format
 *
 * @author Mustafa
 * @version 1.0
 */
public class ToDoListViewAdapter extends BaseAdapter {

    private final ArrayList<ToDoItem> listData;
    private final LayoutInflater layoutInflater;
    private final Context context;

    // comparator for sorting to do items
    private final Comparator<ToDoItem> comparator;

    public ToDoListViewAdapter(Context context, ArrayList<ToDoItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        comparator = new Comparator<ToDoItem>() {
            @Override
            public int compare(ToDoItem toDoItem, ToDoItem t1) {
                return t1.getToDoItemModifiedDate().compareTo(toDoItem.getToDoItemModifiedDate());
            }
        };
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

    /**
     * Wrapper over notifyDataSetChanged() that also sorts to-do items
     */
    public void notifySortedDataSetChanged() {
        listData.sort(comparator);
        this.notifyDataSetChanged();
    }

    /**
     * Adds to-do item to current inventory
     *
     * @param item
     */
    public void addToDoItem(ToDoItem item) {
        listData.add(item);
        this.notifySortedDataSetChanged();
    }

    public List<ToDoItem> getListData() {
        return listData;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final ToDoItem currentItem = listData.get(position);

        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.layout_to_do_list, null);
            holder = new ViewHolder();

            holder.to_do_title = convertView.findViewById(R.id.TextView_to_do_title);
            holder.to_do_date = convertView.findViewById(R.id.TextView_to_do_date);

            // Click listeners for editing items
            holder.to_do_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).showEditItemDialog(currentItem, position, "");
                }
            });
            holder.to_do_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).showEditItemDialog(currentItem, position, "");
                }
            });

            // Long click event listeners for deleting items
            holder.to_do_date.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((MainActivity) context).showDeleteAlertDialog(currentItem, position);
                    return true;
                }
            });
            holder.to_do_title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((MainActivity) context).showDeleteAlertDialog(currentItem, position);
                    return true;
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // display edited/modified date if to do item was modified otherwise display creation date
        holder.to_do_title.setText(currentItem.getToDoItemTitle());
        String displayDate = currentItem.getToDoItemCreationDate().equals(currentItem.getToDoItemModifiedDate()) ?
                currentItem.getToDoItemCreatedDateString() : currentItem.getToDoItemModifiedDateString();
        holder.to_do_date.setText(displayDate);

        return convertView;
    }

    static class ViewHolder {
        TextView to_do_title;
        TextView to_do_date;
    }
}