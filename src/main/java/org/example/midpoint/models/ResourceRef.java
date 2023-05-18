package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

public class ResourceRef {
    @SerializedName("oid")
    String oid;

    public String getOid() {
        return oid;
    }
}
