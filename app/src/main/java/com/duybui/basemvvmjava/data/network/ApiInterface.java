package com.duybui.basemvvmjava.data.network;

import com.duybui.basemvvmjava.data.response.RandomUserResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/api")
    Observable<RandomUserResponse> getRandomUser(@Query("results") int results);
}
