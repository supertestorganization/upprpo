package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

public class AssignmentConstruction {
    @SerializedName("resourceRef")
    ResourceRef resourceRef;

    public ResourceRef getResourceRef() {
        return resourceRef;
    }
}
