package com.duybui.basemvvmjava.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author jpetit
 */

@Database(entities = {UserSchema.class}, version = 1)
public abstract class MvvmDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
