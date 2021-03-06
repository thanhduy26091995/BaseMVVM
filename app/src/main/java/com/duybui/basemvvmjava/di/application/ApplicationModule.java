package com.duybui.basemvvmjava.di.application;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return this.application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return this.application;
    }
}
