package org.example.application;

import org.example.application.fabric.Config;
import org.example.application.fabric.Fabric;
import org.example.application.fabric.FabricImpl;
import org.example.jira.JiraProvider;
import org.example.jira.TicketRequest;
import org.example.midpoint.MidpointProvider;
import org.example.midpoint.MidpointProviderImpl;
import org.example.midpoint.exceptions.BadResource;
import org.example.midpoint.exceptions.BadUser;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, BadResource, BadUser {

        Config.loadConfig();

        Fabric fabric = new FabricImpl();
        JiraProvider jiraProvider = fabric.getJiraProvider();
        MidpointProvider midpointProvider = fabric.getMidpointProvider();

        Timer timer = new Timer("Main timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Iterable<TicketRequest> ticketRequests = jiraProvider.getTickets();

                    for (TicketRequest ticketRequest : ticketRequests) {
                        switch (ticketRequest.getAction()) {
                            case DISABLE ->
                                    midpointProvider.disableAccount(ticketRequest.getUserName(), ticketRequest.getResourceName());
                            case ACTIVATE ->
                                    midpointProvider.activateAccount(ticketRequest.getUserName(), ticketRequest.getResourceName());

                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }, 0, 5000);


    }
}