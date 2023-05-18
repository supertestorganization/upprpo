package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjUsers {
    @SerializedName("object")
    List<MidpointUser> midpointUserList;

    public List<MidpointUser> getMidpointObjectsList() {
        return midpointUserList;
    }
}
