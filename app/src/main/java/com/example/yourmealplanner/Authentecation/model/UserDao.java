package com.example.yourmealplanner.Authentecation.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM User WHERE email = :email LIMIT 1")
    LiveData<User> getUserByEmail(String email);
}

