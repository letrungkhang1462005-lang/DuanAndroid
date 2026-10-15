package com.example.healthgymapp.data.local.remote.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateProfile(UserProfile userProfile);

    @Query("SELECT * FROM user_profile LIMIT 1")
    LiveData<UserProfile> getUserProfile();
}
