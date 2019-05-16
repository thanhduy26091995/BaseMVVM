package com.duybui.basemvvmjava.di.application;

import androidx.room.Room;
import android.content.Context;

import com.duybui.basemvvmjava.data.local.MvvmDatabase;
import com.duybui.basemvvmjava.data.local.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDatabaseModule {
    @Singleton
    @Provides
    MvvmDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, MvvmDatabase.class, MvvmDatabase.class.getSimpleName()).build();
    }

    @Singleton
    @Provides
    UserDao provideUserDao(MvvmDatabase mvvmDatabase) {
        return mvvmDatabase.userDao();
    }
}
