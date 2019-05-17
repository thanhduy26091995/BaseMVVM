package com.duybui.basemvvmjava.ui.users;

import android.app.Application;

import com.duybui.basemvvmjava.data.models.User;
import com.duybui.basemvvmjava.data.network.ApiInterface;
import com.duybui.basemvvmjava.data.response.RandomUserResponse;
import com.duybui.basemvvmjava.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends BaseViewModel {
    private MutableLiveData<List<User>> userList = new MutableLiveData<>();

    @Inject
    ApiInterface apiInterface;

    public UserViewModel(Application application) {
        super(application);
        getPresentationComponent().inject(this);
    }

    public void getRandomUser(int number) {
        apiInterface.getRandomUser(number)
                .subscribeOn(Schedulers.io())
                .map(RandomUserResponse::getUsers)
                .flatMap(Observable::fromIterable)
                .filter(user -> user.getGender().equalsIgnoreCase("female"))
                .toList()
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<User> value) {
                userList.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                setError(e.toString());
            }
        });
    }

    public MutableLiveData<List<User>> getUserList() {
        return userList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
