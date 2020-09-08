package com.comp5216.todolist;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "todolist")
public class ToDoItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "toDoItemID")
    private int toDoItemID;

    @ColumnInfo(name = "toDoItemTitle")
    private String toDoItemTitle;

    private Date toDoItemDate;

    public ToDoItem(String toDoItemTitle) {
        this.toDoItemTitle = toDoItemTitle;
        this.toDoItemDate = new Date();
    }

    public int getToDoItemID() {
        return toDoItemID;
    }

    public void setToDoItemID(int toDoItemID) {
        this.toDoItemID = toDoItemID;
    }

    public String getToDoItemTitle() {
        return toDoItemTitle;
    }

    public void setToDoItemTitle(String toDoItemTitle) {
        this.toDoItemTitle = toDoItemTitle;
        setToDoItemDate(new Date());
    }

    public Date getToDoItemDate() {
        return toDoItemDate;
    }

    public void setToDoItemDate(Date toDoItemDate) {
        this.toDoItemDate = toDoItemDate;
    }

    public String getToDoItemCreatedDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm 'on' EEE dd MMM',' yyyy");
        return sdf.format(toDoItemDate);
    }

    public void setToDoItemCreatedDate(Date toDoItemDate) {
        this.toDoItemDate = toDoItemDate;
    }
}
