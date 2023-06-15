package com.io.example.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class}, version = 1)
@TypeConverters({DateConverter.class, TimestampConverter.class})
public abstract class UserRoomDatabase extends RoomDatabase {//This is single torn pattern
    public abstract UserDao userDao();
    private static volatile UserRoomDatabase INSTANCE;

    static UserRoomDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (UserRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserRoomDatabase.class, "USER_DB").build();
                }
            }
        }
        return INSTANCE;
    }
}
