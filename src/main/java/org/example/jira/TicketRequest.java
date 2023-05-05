package org.example.jira;

public interface TicketRequest {

    public enum Action{DISABLE, ACTIVATE}

    String getUserName();
    String getResourceName();
    Action getAction();

}
