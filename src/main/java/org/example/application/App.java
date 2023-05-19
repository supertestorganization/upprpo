package org.example.application;

import org.example.jira.JiraProvider;
import org.example.jira.Ticket;
import org.example.midpoint.MidpointProvider;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class App implements Runnable {
    private final JiraProvider jiraProvider;
    private final MidpointProvider midpointProvider;

    private Timer timer;

    public App(JiraProvider jiraProvider, MidpointProvider midpointProvider) {
        this.jiraProvider = jiraProvider;
        this.midpointProvider = midpointProvider;
    }

    public void run() {
        timer = new Timer("Timer scheduler");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Iterable<Ticket> tickets = jiraProvider.getTickets();
                    for (Ticket ticket : tickets) {
                        handleTicket(ticket);
                    }

                } catch (IOException e) {
                    //stub, add exception handling later
                    throw new RuntimeException(e);
                }
            }
        }, 0, 5000);
    }

    public void cancel() {
        timer.cancel();
    }

    private void handleTicket(Ticket ticket) throws IOException {
        switch (ticket.getAction()) {
            case DISABLE -> {
                if (null != ticket.getResourceName()) {
                    midpointProvider.disableAccount(ticket.getUserName(), ticket.getResourceName());
                } else {
                    midpointProvider.disableUser(ticket.getUserName());
                }
            }

            case ENABLE -> {
                if (null != ticket.getResourceName()) {
                    midpointProvider.activateAccount(ticket.getUserName(), ticket.getResourceName());
                } else {
                    midpointProvider.activateUser(ticket.getUserName());
                }
            }
            default -> {
                //nothing to do
            }
        }
    }
}

