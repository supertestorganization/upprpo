package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

public class GetMidpointResourcesResponse {
    @SerializedName("object")
    ObjRes objRes;

    public ObjRes getObjRes() {
        return objRes;
    }
}
