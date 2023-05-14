package org.example.jira.impl.model.ticket.internal;

import com.google.gson.annotations.SerializedName;

public class Issue {
    @SerializedName("key")
    private String key;
    @SerializedName("summary")
    private String summary;

    Issue(){}

    public String getKey() {
        return key;
    }

    public String getSummary() {
        return summary;
    }
}
