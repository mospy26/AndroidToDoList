package com.comp5216.todolist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoItemDao {
    @Query("SELECT * FROM todolist")
    List<ToDoItem> listAll();

    @Insert
    void insert(ToDoItem toDoItem);

    @Insert
    void insertAll(ToDoItem... toDoItems);

    @Query("DELETE FROM todolist")
    void deleteAll();

    @Update
    void update(ToDoItem item);
}
