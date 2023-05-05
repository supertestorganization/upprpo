package org.example.jira;


import java.io.IOException;
import java.util.Collection;

public interface JiraProvider {
    Iterable<TicketRequest> getTickets() throws IOException;
}
