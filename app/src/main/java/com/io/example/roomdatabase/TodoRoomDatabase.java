package com.io.example.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class}, version = 1)
public abstract class TodoRoomDatabase extends RoomDatabase {//This is single torn pattern
    public abstract TodoDao todoDao();
    private static volatile TodoRoomDatabase INSTANCE;

    static TodoRoomDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (TodoRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodoRoomDatabase.class, "TODO_DB").build();
                }
            }
        }
        return INSTANCE;
    }
}
