package com.duybui.basemvvmjava;

import android.os.Bundle;

import com.duybui.basemvvmjava.ui.base.BaseActivity;
import com.duybui.basemvvmjava.ui.base.DialogsManager;
import com.duybui.basemvvmjava.ui.base.ServerErrorDialogFragment;
import com.duybui.basemvvmjava.ui.base.ViewModelFactory;
import com.duybui.basemvvmjava.ui.users.UserAdapter;
import com.duybui.basemvvmjava.ui.users.UserViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @Inject
    DialogsManager dialogsManager;
    @Inject
    UserAdapter userAdapter;
    @Inject
    ViewModelFactory viewModelFactory;

    UserViewModel userViewModel;

    @BindView(R.id.rv_users)
    RecyclerView mRecycler;


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
        userViewModel.getUserList().observe(this, users ->
                userAdapter.setData(users));
        //show error message
        userViewModel.getError().observe(this, error ->
                dialogsManager.showDialog(ServerErrorDialogFragment.newInstance("Error", error)));

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
