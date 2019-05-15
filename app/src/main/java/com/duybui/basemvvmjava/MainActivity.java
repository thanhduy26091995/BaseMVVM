package com.duybui.basemvvmjava;

import android.os.Bundle;

import com.duybui.basemvvmjava.ui.base.BaseActivity;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPresentationComponent().inject(this);
    }
}
