package com.duybui.basemvvmjava.ui.base;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.duybui.basemvvmjava.MyApplication;
import com.duybui.basemvvmjava.di.application.ApplicationComponent;
import com.duybui.basemvvmjava.di.presentation.PresentationComponent;
import com.duybui.basemvvmjava.di.presentation.PresentationModule;


public class BaseActivity extends AppCompatActivity {

    private boolean mIsInjectorUsed;

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule(this));

    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }
}
