package org.example.jira.impl;

import org.example.application.fabric.JiraConfig;
import org.example.jira.Ticket;
import org.example.jira.impl.model.ticket.TicketImpl;
import org.example.jira.impl.model.ticket.internal.Issue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

class TicketScanner {

    private final JiraWebAPI jiraWebAPI;

    private final String authToken;
    private static final String SEARCH_JQL = JiraConfig.SEARCH_JQL;

    private static final int USERNAME_ONLY = 2;

    public TicketScanner(JiraWebAPI jiraWebAPI, String authToken){
        this.jiraWebAPI = Objects.requireNonNull(jiraWebAPI);
        this.authToken = authToken;
    }

    public Iterable<Ticket> getTickets() throws IOException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        Issue[] issues;
        try {
            issues = Objects.requireNonNull(jiraWebAPI.getTickets(authToken, SEARCH_JQL).execute().body()).getSections()[0].getIssues();
        } catch (NullPointerException e){
            return tickets;
        }

        for (Issue issue : issues) {
            String[] tokens = issue.getSummary().replaceAll("\\s+", "").split("\\|");

            if (!isValidLen(tokens.length)) {
                continue;
            }

            int actionIndex = 0;
            Ticket.Action action = parseAction(tokens[actionIndex]);
            if (action == Ticket.Action.BAD_ACTION){
                continue;
            }

            int usernameIndex = 1;
            if (tokens.length == USERNAME_ONLY) {
                tickets.add(new TicketImpl(issue.getKey(), tokens[usernameIndex], null, action));
            } else{
                int resourceIndex = 2;
                tickets.add(new TicketImpl(issue.getKey(), tokens[usernameIndex], tokens[resourceIndex], action));
            }
        }
        return tickets;
    }

    private boolean isValidLen(int len){
        int usernameAndResource = 3;
        return len == USERNAME_ONLY || len == usernameAndResource;
    }

    private Ticket.Action parseAction(String str){
        if (null == str){
            return Ticket.Action.BAD_ACTION;
        }

        if (str.equals("ENABLE")) {
            return Ticket.Action.ENABLE;
        } else if (str.equals("DISABLE")) {
            return Ticket.Action.DISABLE;
        }

        return Ticket.Action.BAD_ACTION;
    }


}
