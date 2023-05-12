package org.example.jira;


import java.io.IOException;

public interface JiraProvider {
    Iterable<Ticket> getTickets() throws IOException;

    void makeTicketDone(String ticketKey) throws IOException;

    void makeTicketFailed(String ticketKey) throws IOException;

    void changeTicketDescription(String ticketKey, String newDescription) throws IOException;

}
