package com.duybui.basemvvmjava;

import android.app.Application;

import com.duybui.basemvvmjava.di.application.ApiModule;
import com.duybui.basemvvmjava.di.application.ApplicationComponent;
import com.duybui.basemvvmjava.di.application.ApplicationModule;
import com.duybui.basemvvmjava.di.application.DaggerApplicationComponent;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
