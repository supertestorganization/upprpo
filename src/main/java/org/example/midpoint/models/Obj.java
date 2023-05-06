package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Obj {
    @SerializedName("object")
    List<User> userList;

    public List<User> getUserList() {
        return userList;
    }
}
