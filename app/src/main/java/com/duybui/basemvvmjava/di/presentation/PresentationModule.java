package com.duybui.basemvvmjava.di.presentation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.duybui.basemvvmjava.data.network.ApiRepository;
import com.duybui.basemvvmjava.ui.base.DialogsManager;
import com.duybui.basemvvmjava.ui.users.UserAdapter;

import javax.inject.Singleton;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    private FragmentActivity mActivity;

    public PresentationModule(FragmentActivity fragmentActivity) {
        mActivity = fragmentActivity;
    }

    public PresentationModule() {
    }

    @Provides
    FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    @Provides
    Activity getActivity() {
        return mActivity;
    }

    @Provides
    Context context(Activity activity) {
        return activity;
    }

    @Provides
    DialogsManager dialogsManager(FragmentManager fragmentManager) {
        return new DialogsManager(fragmentManager);
    }

    @Provides
    UserAdapter userAdapter(Activity activity) {
        return new UserAdapter(activity);
    }

}

