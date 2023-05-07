package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Obj {
    @SerializedName("object")
    List<MidpointObject> midpointObjectList;

    public List<MidpointObject> getMidpointObjectsList() {
        return midpointObjectList;
    }
}
