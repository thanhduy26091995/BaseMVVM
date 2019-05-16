package com.duybui.basemvvmjava.data.response;

import com.duybui.basemvvmjava.data.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomUserResponse {
    @SerializedName("results")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}
