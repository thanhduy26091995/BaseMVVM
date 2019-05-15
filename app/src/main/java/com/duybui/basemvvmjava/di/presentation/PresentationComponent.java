package com.duybui.basemvvmjava.di.presentation;

import com.duybui.basemvvmjava.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {PresentationModule.class, ViewModelModule.class})
public interface PresentationComponent {
    void inject(MainActivity mainActivity);
}
