package com.duybui.basemvvmjava.di.presentation;

import androidx.lifecycle.ViewModel;

import com.duybui.basemvvmjava.data.network.ApiInterface;
import com.duybui.basemvvmjava.ui.base.ViewModelFactory;
import com.duybui.basemvvmjava.ui.users.UserViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    ViewModel userViewModel(ApiInterface apiInterface) {
        return new UserViewModel(apiInterface);
    }
}
