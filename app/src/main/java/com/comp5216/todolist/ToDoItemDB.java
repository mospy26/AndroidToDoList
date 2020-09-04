package com.comp5216.todolist;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;


@Database(entities = {ToDoItem.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class ToDoItemDB extends RoomDatabase {
    private static final String DATABASE_NAME = "todoitem_db";
    private static ToDoItemDB DBINSTANCE;

    public abstract ToDoItemDao toDoItemDao();

    public static ToDoItemDB getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (ToDoItemDB.class) {
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ToDoItemDB.class, DATABASE_NAME).build();
            }
        }
        return DBINSTANCE;
    }

    public static void destroyInstance() {
        DBINSTANCE = null;
    }
}