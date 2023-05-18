package org.example.midpoint.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MidpointUser {
    @SerializedName("oid")
    String oid;

    @SerializedName("name")
    String name;

    @SerializedName("assignment")
    List<Assignment> assignments;

    public String getName() {
        return name;
    }

    public String getOid() {
        return oid;
    }

    public List<Assignment> getAssignmentList() {
        return assignments;
    }
}
