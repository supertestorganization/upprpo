package org.example.application;

import org.example.application.fabric.Fabric;
import org.example.application.fabric.FabricImpl;
import org.example.application.fabric.JiraConfig;
import org.example.application.fabric.MidpointConfig;
import org.example.jira.JiraProvider;
import org.example.jira.Ticket;
import org.example.midpoint.MidpointProvider;
import org.example.midpoint.exceptions.BadResource;
import org.example.midpoint.exceptions.BadUser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, BadResource, BadUser {

        MidpointConfig.loadConfig();
        JiraConfig.loadConfig();

        Fabric fabric = new FabricImpl();
        JiraProvider jiraProvider = fabric.getJiraProvider();
        MidpointProvider midpointProvider = fabric.getMidpointProvider();

        Timer timer = new Timer("Main timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Iterable<Ticket> tickets = jiraProvider.getTickets();

                    for (Ticket ticket : tickets) {
                        switch (ticket.getAction()) {
                            case DISABLE -> {
                                if (null != ticket.getResourceName()){
                                    midpointProvider.disableAccount(ticket.getUserName(), ticket.getResourceName());
                                } else {
                                    midpointProvider.disableUser(ticket.getUserName());
                                }
                            }

                            case ENABLE -> {
                                if (null != ticket.getResourceName()){
                                    midpointProvider.activateAccount(ticket.getUserName(), ticket.getResourceName());
                                } else {
                                    midpointProvider.activateUser(ticket.getUserName());
                                }
                            }


                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }, 0, 5000);


    }
}