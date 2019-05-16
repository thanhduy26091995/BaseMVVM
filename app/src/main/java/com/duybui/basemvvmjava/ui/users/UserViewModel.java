package com.duybui.basemvvmjava.ui.users;

import com.duybui.basemvvmjava.data.models.User;
import com.duybui.basemvvmjava.data.network.ApiInterface;
import com.duybui.basemvvmjava.data.response.RandomUserResponse;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> userList = new MutableLiveData<>();

    private final ApiInterface apiInterface;

    public UserViewModel(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public void getRandomUser(int number) {
        apiInterface.getRandomUser(number).enqueue(new Callback<RandomUserResponse>() {
            @Override
            public void onResponse(Call<RandomUserResponse> call, Response<RandomUserResponse> response) {
                userList.setValue(response.body().getUsers());
            }

            @Override
            public void onFailure(Call<RandomUserResponse> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<User>> getUserList() {
        return userList;
    }
}
