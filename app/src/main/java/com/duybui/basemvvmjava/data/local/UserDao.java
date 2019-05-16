package com.duybui.basemvvmjava.data.local;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * from UserSchema")
    List<UserSchema> getUserList();
}
