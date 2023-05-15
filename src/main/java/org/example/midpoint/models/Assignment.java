package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

public class Assignment {

    @SerializedName("@id")
    Integer id;
    @SerializedName("construction")
    AssignmentConstruction construction;

    @SerializedName("activation")
    AssignmentActivation activation;

    public Integer getId() {
        return id;
    }

    public AssignmentActivation getActivation() {
        return activation;
    }

    public AssignmentConstruction getConstruction() {
        return construction;
    }
}
