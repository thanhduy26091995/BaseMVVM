package com.duybui.basemvvmjava.ui.base;

import android.app.Application;

import com.duybui.basemvvmjava.MyApplication;
import com.duybui.basemvvmjava.data.network.ApiFunction;
import com.duybui.basemvvmjava.data.network.ApiListener;
import com.duybui.basemvvmjava.data.network.ApiRepository;
import com.duybui.basemvvmjava.data.network.ApiStatus;
import com.duybui.basemvvmjava.di.presentation.PresentationComponent;
import com.duybui.basemvvmjava.di.presentation.PresentationModule;

import javax.inject.Inject;

import androidx.annotation.UiThread;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public abstract class BaseViewModel extends AndroidViewModel implements ApiListener {
    protected MutableLiveData<String> error = new MutableLiveData<>();
    private boolean mIsInjectorUsed;

    public BaseViewModel(Application application) {
        super(application);
    }


    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return getMvvmApplication().getApplicationComponent()
                .newPresentationComponent(new PresentationModule());

    }


    protected MyApplication getMvvmApplication() {
        return (MyApplication) getApplication();
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void setError(String errorStr) {
        error.setValue(errorStr);
    }

    @Override
    public void onResponseListener(ApiFunction apifunction, ApiStatus statusId, Object object) {
        switch (statusId) {
            case CALL_API_RESULT_FAILURE: {
                setError(object.toString());
                break;
            }
        }
    }
}
