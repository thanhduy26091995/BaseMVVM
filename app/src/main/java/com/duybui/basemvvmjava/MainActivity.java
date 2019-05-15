package com.duybui.basemvvmjava;

import android.os.Bundle;

import com.duybui.basemvvmjava.ui.base.BaseActivity;
import com.duybui.basemvvmjava.ui.base.DialogsManager;
import com.duybui.basemvvmjava.ui.base.ServerErrorDialogFragment;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;
    @Inject
    DialogsManager dialogsManager;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPresentationComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dialogsManager.showDialog(ServerErrorDialogFragment.newInstance("HAHAHA", "Should I buy macbook?"));
    }
}
