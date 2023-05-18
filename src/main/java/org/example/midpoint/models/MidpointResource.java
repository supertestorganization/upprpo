package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

public class MidpointResource {
    @SerializedName("oid")
    String resourceOid;

    @SerializedName("name")
    String resourceName;

    public String getResourceOid() {
        return resourceOid;
    }

    public String getResourceName() {
        return resourceName;
    }
}
