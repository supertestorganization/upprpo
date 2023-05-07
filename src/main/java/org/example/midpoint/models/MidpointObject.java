package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

public class MidpointObject {
    @SerializedName("oid")
    String oid;

    @SerializedName("name")
    String name;

    public String getName() {
        return name;
    }

    public String getOid() {
        return oid;
    }
}
