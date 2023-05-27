package org.example.application;

import lombok.extern.log4j.Log4j2;
import org.example.jira.JiraProvider;
import org.example.jira.Ticket;
import org.example.midpoint.MidpointProvider;
import org.example.midpoint.OperationResult;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@Log4j2
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
                        OperationResult result = handleTicket(ticket);
                        if (OperationResult.OPERATION_STATUS.SUCCEED.equals(result.status())) {
                            jiraProvider.makeTicketDone(ticket.getKey());
                            jiraProvider.setTicketDescription(ticket.getKey(), result.msg());
                        } else {
                            log.info("Failed to process ticket: " + ticket.getKey() + '\n' + result.msg());
                            jiraProvider.makeTicketFailed(ticket.getKey());
                            jiraProvider.setTicketDescription(ticket.getKey(), result.msg());
                        }
                    }

                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
        }, 0, 5000);
    }

    public void cancel() {
        timer.cancel();
    }

    private OperationResult handleTicket(Ticket ticket) throws IOException {
        switch (ticket.getAction()) {
            case DISABLE -> {
                if (null != ticket.getResourceName()) {
                    return midpointProvider.disableAccount(ticket.getUserName(), ticket.getResourceName());
                } else {
                    return midpointProvider.disableUser(ticket.getUserName());
                }
            }

            case ENABLE -> {
                if (null != ticket.getResourceName()) {
                    return midpointProvider.activateAccount(ticket.getUserName(), ticket.getResourceName());
                } else {
                    return midpointProvider.activateUser(ticket.getUserName());
                }
            }
            default -> {
                log.info("Caught bad ticket: "+ ticket.getAction().name());
                return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "Unknown action");
            }
        }
    }
}

