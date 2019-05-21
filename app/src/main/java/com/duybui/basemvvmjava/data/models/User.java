package com.duybui.basemvvmjava.data.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("gender")
    private String gender;
    @SerializedName("picture")
    private Picture picture;

    public User(String email, String phone, String gender, Picture picture) {
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public Picture getPicture() {
        return picture;
    }
}
