package com.comp5216.todolist;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Type converter for storing Date in database
 *
 * @author Mustafa
 * @version 1.0
 */
public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}