package org.example.jira.impl.model.ticket;

import org.example.jira.Ticket;

public class TicketImpl implements Ticket {

    private final String key;
    private final String userName;
    private final String resourceName;
    private final Action action;

    public TicketImpl(String key, String userName, String resourceName, Action action){
        this.key = key;
        this.action = action;
        this.resourceName = resourceName;
        this.userName = userName;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

    @Override
    public Action getAction() {
        return action;
    }
}
