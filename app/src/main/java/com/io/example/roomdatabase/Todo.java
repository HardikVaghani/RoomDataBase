package com.io.example.roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_uid")
    private int uId;

    @ColumnInfo(name = "todo_text")
    private String text;

    @ColumnInfo(name = "todo_completed")
    private boolean completed;

    public Todo(String text, boolean completed) {
        this.text = text;
        this.completed = completed;
    }

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "\nTodo\t{" +
                "uId=" + uId +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                '}';
    }
}
