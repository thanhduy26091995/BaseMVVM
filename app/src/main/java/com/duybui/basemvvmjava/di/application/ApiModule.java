package com.duybui.basemvvmjava.di.application;


import android.app.Application;

import com.duybui.basemvvmjava.data.network.ApiInterface;
import com.duybui.basemvvmjava.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(AppConstants.BASE_URL)
                .build();
    }

    @Singleton
    @Provides
    Gson gson() {
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    OkHttpClient okHttpClient(Cache cache) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
    }

    @Singleton
    @Provides
    Cache cache(Application application) {
        long cacheSize = (10 * 1024 * 1024);
        File file = new File(application.getCacheDir(), "http-cache");
        return new Cache(file, cacheSize);
    }

    @Singleton
    @Provides
    ApiInterface getAPIInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }
}
