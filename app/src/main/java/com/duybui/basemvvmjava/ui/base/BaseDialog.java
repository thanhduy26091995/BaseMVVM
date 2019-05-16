package com.duybui.basemvvmjava.ui.base;

import androidx.annotation.UiThread;
import androidx.fragment.app.DialogFragment;

import com.duybui.basemvvmjava.MyApplication;
import com.duybui.basemvvmjava.di.application.ApplicationComponent;
import com.duybui.basemvvmjava.di.presentation.PresentationComponent;
import com.duybui.basemvvmjava.di.presentation.PresentationModule;

public abstract class BaseDialog extends DialogFragment {

    private boolean mIsInjectorUsed;

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule(getActivity()));

    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getActivity().getApplication()).getApplicationComponent();
    }
}
