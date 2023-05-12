package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

public class Operation {
    @SerializedName("objectName")
    String objectName;

    @SerializedName("resourceOid")
    String resourceOid;

    @SerializedName("resourceName")
    String resourceName;

    public String getObjectName() {
        return objectName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceOid() {
        return resourceOid;
    }
}
