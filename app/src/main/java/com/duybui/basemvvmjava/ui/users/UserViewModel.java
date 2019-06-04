package com.duybui.basemvvmjava.ui.users;

import android.app.Application;

import com.duybui.basemvvmjava.data.models.User;
import com.duybui.basemvvmjava.data.network.ApiFunction;
import com.duybui.basemvvmjava.data.network.ApiRepository;
import com.duybui.basemvvmjava.data.network.ApiStatus;
import com.duybui.basemvvmjava.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends BaseViewModel {
    private MutableLiveData<List<User>> userList = new MutableLiveData<>();

    @Inject
    ApiRepository apiRepository;

    public UserViewModel(Application application) {
        super(application);
        getPresentationComponent().inject(this);
        //listen response
        apiRepository.setApiListener(this);
    }

    @Override
    public void onResponseListener(ApiFunction apifunction, ApiStatus statusId, Object object) {
        super.onResponseListener(apifunction, statusId, object);
        if (apifunction == ApiFunction.GET_RANDOM_USER && statusId == ApiStatus.CALL_API_RESULT_SUCCESS) {
            userList.setValue((List<User>) object);
        }
    }

    public void getRandomUser(int number) {
        apiRepository.getRandomUser(number);
    }

    public MutableLiveData<List<User>> getUserList() {
        return userList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
