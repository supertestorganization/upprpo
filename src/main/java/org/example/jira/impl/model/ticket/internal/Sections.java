package org.example.jira.impl.model.ticket.internal;

import com.google.gson.annotations.SerializedName;

public class Sections {

    @SerializedName("issues")
    private Issue[] issues;

    public Issue[] getIssues() {
        return issues;
    }
}
