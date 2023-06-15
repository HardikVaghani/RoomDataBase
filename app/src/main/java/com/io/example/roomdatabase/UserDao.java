package com.io.example.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User User); // Create

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers(); // Read

    @Update
    void updateUser(User User);// Update

    @Delete
    void deleteUser(User User); // Delete



    @Insert
    void insertMultipleUsers(List<User> UserList);

    @Query("SELECT * FROM User_table where user_status LIKE 1")
    List<User> getAllCompletedUsers();

    @Query("SELECT * FROM User_table where user_status LIKE 1")
    LiveData<List<User>> getAllActiveUsers(); // For ViewModel(LiveData)


    @Query("SELECT * FROM User_table")
    LiveData<List<User>> findAllUsers(); // For ViewModel(LiveData)



    @Query("SELECT * FROM User_table where user_id LIKE :userUId")
    User findUserById(int userUId);

    @Query("SELECT * FROM User_table where user_user_name LIKE :userUserName")
    User findUserByUserName(String userUserName);

    @Query("SELECT * FROM User_table where user_email LIKE :userEmail")
    User findUserByEmail(String userEmail);

    @Query("SELECT * FROM User_table where user_phone LIKE :userPhone")
    User findUserByPhone(String userPhone);



}
