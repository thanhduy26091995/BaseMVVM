package com.duybui.basemvvmjava.data.network;

import com.duybui.basemvvmjava.MyApplication;
import com.duybui.basemvvmjava.data.models.User;
import com.duybui.basemvvmjava.data.response.BaseResponseObject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiRepository {

    private ApiListener apiListener;

    public void setApiListener(ApiListener apiListener) {
        this.apiListener = apiListener;
    }

    private final ApiInterface apiInterface;

    public ApiRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public void getRandomUser(int number) {
        Observable<BaseResponseObject<List<User>>> observable = apiInterface.getRandomUser(number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        handleResponseObject(observable, ApiFunction.GET_RANDOM_USER);
    }

    private <T> void handleResponseObject(Observable<T> observable, ApiFunction apiFunction) {
        observable.subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(T value) {
                if (value != null) {
                    BaseResponseObject<T> responseObject = (BaseResponseObject<T>) value;
                    if (responseObject.getCode() == 0) {
                        apiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_SUCCESS, ((BaseResponseObject<T>) value).getData());
                    } else {
                        apiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, responseObject.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                apiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
