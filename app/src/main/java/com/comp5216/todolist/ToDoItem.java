package com.comp5216.todolist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "todolist")
public class ToDoItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "toDoItemID")
    private int toDoItemID;

    @ColumnInfo(name = "toDoItemTitle")
    private String toDoItemTitle;

    private Date toDoItemCreatedDate;

    public ToDoItem(String toDoItemTitle){
        this.toDoItemTitle = toDoItemTitle;
        this.toDoItemCreatedDate = new Date();
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
    }

    public Date getToDoItemCreatedDate() {
        return toDoItemCreatedDate;
    }

    public void setToDoItemCreatedDate(Date toDoItemCreatedDate) {
        this.toDoItemCreatedDate = toDoItemCreatedDate;
    }
}
