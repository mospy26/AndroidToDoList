package com.comp5216.todolist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    private Date toDoItemCreationDate;

    private Date toDoItemModifiedDate;

    public ToDoItem(String toDoItemTitle) {
        this.toDoItemTitle = toDoItemTitle;
        Date now = new Date();
        this.toDoItemCreationDate = now;
        this.toDoItemModifiedDate = now;
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
        setToDoItemModifiedDate(new Date());
    }

    public Date getToDoItemCreationDate() {
        return toDoItemCreationDate;
    }

    public void setToDoItemCreationDate(Date toDoItemCreationDate) {
        this.toDoItemCreationDate = toDoItemCreationDate;
    }

    public Date getToDoItemModifiedDate() {
        return toDoItemModifiedDate;
    }

    public void setToDoItemModifiedDate(Date toDoItemModifiedDate) {
        this.toDoItemModifiedDate = toDoItemModifiedDate;
    }

    public String getToDoItemCreatedDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm 'on' EEE dd MMM',' yyyy");
        return sdf.format(toDoItemCreationDate);
    }

    public String getToDoItemModifiedDateString() {
        if (toDoItemModifiedDate == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm 'on' EEE dd MMM',' yyyy '(edited)'");
        return sdf.format(toDoItemModifiedDate);
    }

    public void setToDoItemCreatedDate(Date toDoItemCreationDate) {
        this.toDoItemCreationDate = toDoItemCreationDate;
    }
}
