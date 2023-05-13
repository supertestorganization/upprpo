package org.example.application.fabric;

import org.example.jira.JiraProvider;
import org.example.jira.impl.JiraProviderImpl;
import org.example.midpoint.MidpointProvider;
import org.example.midpoint.MidpointProviderImpl;

public class FabricImpl implements Fabric{

    @Override
    public JiraProvider getJiraProvider() {
        return new JiraProviderImpl();
    }

    @Override
    public MidpointProvider getMidpointProvider() {
        return new MidpointProviderImpl();
    }
}
