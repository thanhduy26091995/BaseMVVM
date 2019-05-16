package com.duybui.basemvvmjava;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import com.duybui.basemvvmjava.data.network.ApiInterface;
import com.duybui.basemvvmjava.data.response.RandomUserResponse;
import com.duybui.basemvvmjava.ui.base.BaseActivity;
import com.duybui.basemvvmjava.ui.base.DialogsManager;
import com.duybui.basemvvmjava.ui.base.ViewModelFactory;
import com.duybui.basemvvmjava.ui.users.UserAdapter;
import com.duybui.basemvvmjava.ui.users.UserViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;
    @Inject
    DialogsManager dialogsManager;
    @Inject
    UserAdapter userAdapter;
    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.rv_users)
    RecyclerView mRecycler;

    private static final String TAG = MainActivity.class.getSimpleName();
    @Inject UserViewModel userViewModel;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        setupRecyclerView();
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        //call API to get data
        userViewModel.getRandomUser(10);
        //listen live data
        userViewModel.getUserList().observe(this, users -> {
            userAdapter.setData(users);
        });
    }


    private void setupRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(userAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
