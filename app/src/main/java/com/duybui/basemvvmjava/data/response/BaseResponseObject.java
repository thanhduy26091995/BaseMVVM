package com.duybui.basemvvmjava.data.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponseObject<T> {
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;
    @SerializedName("results")
    private T data;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }
}
