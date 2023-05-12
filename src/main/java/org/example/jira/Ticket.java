package org.example.jira;

public interface Ticket {

    enum Action{DISABLE, ENABLE}

    String getKey();
    String getUserName();
    String getResourceName();
    Action getAction();

}
