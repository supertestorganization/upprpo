package org.example.jira.impl.model.ticket;

import org.example.jira.Ticket;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicketImplTest {

    private final String key = "key";
    private final String userName = "userName";
    private final String resourceName = "resourceName";
    private final Ticket.Action action = Ticket.Action.ENABLE;
    private Ticket ticket;

    @Before
    public void create_ticket(){
        ticket = new TicketImpl(key,userName,resourceName,action);
    }

    @Test
    public void ticket_get_methods(){
        assertEquals(ticket.getKey(), key);
        assertEquals(ticket.getResourceName(), resourceName);
        assertEquals(ticket.getAction(), action);
        assertEquals(ticket.getUserName(), userName);
    }

}