package com.duybui.basemvvmjava.data.network;

import com.duybui.basemvvmjava.data.models.User;
import com.duybui.basemvvmjava.data.response.BaseResponseObject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/api")
    Observable<BaseResponseObject<List<User>>> getRandomUser(@Query("results") int results);
}
