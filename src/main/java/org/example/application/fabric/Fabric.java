package org.example.application.fabric;

import org.example.jira.JiraProvider;
import org.example.midpoint.MidpointProvider;

public interface Fabric {
    JiraProvider getJiraProvider();
    MidpointProvider getMidpointProvider();
}
