package org.example.jira;

public interface Ticket {

    enum Action{DISABLE, ENABLE, BAD_ACTION}

    String getKey();
    String getUserName();
    String getResourceName();
    Action getAction();

}
