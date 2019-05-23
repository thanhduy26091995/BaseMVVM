package com.duybui.basemvvmjava;

import android.app.Application;

import com.duybui.basemvvmjava.di.application.ApiModule;
import com.duybui.basemvvmjava.di.application.ApplicationComponent;
import com.duybui.basemvvmjava.di.application.ApplicationModule;
import com.duybui.basemvvmjava.di.application.DaggerApplicationComponent;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //init Dagger 2
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule())
                .build();
        applicationComponent.inject(this);
        //init custom font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IBMPlexSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
