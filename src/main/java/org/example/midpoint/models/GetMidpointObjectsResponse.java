package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

public class GetMidpointObjectsResponse {
    @SerializedName("object")
    Obj obj;

    public Obj getObj() {
        return obj;
    }
}
