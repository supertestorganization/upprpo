package org.example.jira.impl.model.transition;

import com.google.gson.annotations.SerializedName;

public class TransitionSearchResponse {
    @SerializedName("transitions")
    private Transition[] transitions;

    public Transition[] getTransitions() {
        return transitions;
    }
}
