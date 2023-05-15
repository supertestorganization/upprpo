package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjRes {
    @SerializedName("object")
    List<MidpointResource> resourceObjs;

    public List<MidpointResource> getMidpointResources() {
        return resourceObjs;
    }
}
