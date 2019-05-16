package com.duybui.basemvvmjava.ui.users;

import android.util.Log;

import com.duybui.basemvvmjava.data.models.User;
import com.duybui.basemvvmjava.data.network.ApiInterface;
import com.duybui.basemvvmjava.data.response.RandomUserResponse;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> userList = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    private CompositeDisposable disposable;


    private final ApiInterface apiInterface;

    public UserViewModel(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
        disposable = new CompositeDisposable();
    }

    public void getRandomUser(int number) {
        apiInterface.getRandomUser(number)
                .subscribeOn(Schedulers.io())
                .map(randomUserResponse -> randomUserResponse.getUsers())
                .flatMap(response -> Observable.fromIterable(response))
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
                error.setValue(e.toString());
            }
        });
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<List<User>> getUserList() {
        return userList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
