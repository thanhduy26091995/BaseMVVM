package com.duybui.basemvvmjava.di.application;


import android.app.Application;

import com.duybui.basemvvmjava.di.presentation.PresentationComponent;
import com.duybui.basemvvmjava.di.presentation.PresentationModule;
import com.duybui.basemvvmjava.di.service.ServiceComponent;
import com.duybui.basemvvmjava.di.service.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    PresentationComponent newPresentationComponent(PresentationModule presentationModule);

    ServiceComponent newServiceComponent(ServiceModule serviceModule);

    void inject(Application application);
}
