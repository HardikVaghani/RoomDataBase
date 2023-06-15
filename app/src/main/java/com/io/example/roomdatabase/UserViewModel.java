package com.io.example.roomdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    UserRoomDatabase userRoomDatabase;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRoomDatabase = userRoomDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<List<User>> getAllUsers(){
        return userRoomDatabase.userDao().findAllUsers();
    }
    public LiveData<List<User>> getAllActiveUser(){
        return userRoomDatabase.userDao().getAllActiveUsers();
    }

}
