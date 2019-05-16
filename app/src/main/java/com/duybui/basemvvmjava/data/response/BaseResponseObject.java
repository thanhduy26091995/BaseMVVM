package com.duybui.basemvvmjava.data.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponseObject<T> {
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private T data;

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
