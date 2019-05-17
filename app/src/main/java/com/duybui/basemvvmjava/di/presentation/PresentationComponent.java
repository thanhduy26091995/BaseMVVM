package com.duybui.basemvvmjava.di.presentation;

import com.duybui.basemvvmjava.MainActivity;
import com.duybui.basemvvmjava.ui.base.BaseViewModel;
import com.duybui.basemvvmjava.ui.users.UserViewModel;

import dagger.Subcomponent;

@Subcomponent(modules = {PresentationModule.class, ViewModelModule.class})
public interface PresentationComponent {
    void inject(MainActivity mainActivity);

    void inject(UserViewModel userViewModel);

}
