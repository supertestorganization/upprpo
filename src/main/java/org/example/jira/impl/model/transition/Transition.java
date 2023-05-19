package org.example.jira.impl.model.transition;

import com.google.gson.annotations.SerializedName;

public class Transition {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
