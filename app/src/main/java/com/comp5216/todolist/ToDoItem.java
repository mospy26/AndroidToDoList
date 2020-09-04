package com.comp5216.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoItem {
    private String title;
    private final Date created;
    private Date modified;

    public ToDoItem(String title, Date created, Date modified) {
        this.title = title;
        this.created = created;
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedString() {
        String pattern = "HH:mm E dd MMM y";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(created);
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
