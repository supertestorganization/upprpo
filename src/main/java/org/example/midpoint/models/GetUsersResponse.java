package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUsersResponse {
    @SerializedName("object")
    Obj obj;

    public Obj getObj() {
        return obj;
    }
}
