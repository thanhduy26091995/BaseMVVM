package com.duybui.basemvvmjava.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * @author jpetit
 */

@Database(entities = {User.class}, version = 1)
public abstract class MvvmDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
