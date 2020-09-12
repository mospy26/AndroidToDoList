package com.comp5216.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * DAO for to-do list database
 *
 * @author Mustafa
 * @version 1.0
 */
@Dao
public interface ToDoItemDao {
    @Query("SELECT * FROM todolist ORDER BY toDoItemModifiedDate DESC")
    List<ToDoItem> listAll();

    @Insert
    void insert(ToDoItem toDoItem);

    @Insert
    void insertAll(ToDoItem... toDoItems);

    @Update
    void update(ToDoItem item);

    @Delete
    void delete(ToDoItem item);
}
