package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

public class GetMidpointUserResponse {
    @SerializedName("object")
    ObjUsers obj;

    public ObjUsers getObj() {
        return obj;
    }
}
