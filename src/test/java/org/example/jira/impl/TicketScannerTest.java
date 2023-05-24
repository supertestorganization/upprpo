package org.example.jira.impl;

import com.google.gson.Gson;
import org.example.jira.Ticket;
import org.example.jira.impl.model.ticket.TicketSearchResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TicketScannerTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private JiraWebAPI jiraWebAPI;
    private final Gson gson = new Gson();


    @Test
    public void get_disable_username_resource_tickets() throws IOException {
        String json = "{\"sections\":[{\"label\":\"History Search\",\"sub\":\"Showing 1 of 1 matching issues\",\"id\":\"hs\",\"issues\":[{\"id\":10037,\"key\":\"NSU-38\",\"keyHtml\":\"NSU-38\",\"img\":\"/rest/api/2/universal_avatar/view/type/issuetype/avatar/10300?size=medium\",\"summary\":\"DISABLE |USERNAME|RESOURCE\",\"summaryText\":\"DISABLE |USERNAME|RESOURCE\"}]}]}";

        TicketSearchResponse response = gson.fromJson(json, TicketSearchResponse.class);

        Mockito.when(jiraWebAPI.getTickets(any(),any()).execute().body()).thenReturn(response);
        TicketScanner ticketScanner = new TicketScanner(jiraWebAPI, "");
        Iterable<Ticket> tickets = ticketScanner.getTickets();

        int count = 0;
        for (Ticket ticket : tickets){
            assertEquals("USERNAME",ticket.getUserName());
            assertEquals("RESOURCE",ticket.getResourceName());
            assertEquals("NSU-38",ticket.getKey());
            assertEquals(Ticket.Action.DISABLE,ticket.getAction());
            count++;
        }
        assertEquals(1,count);
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTickets(any(),any());
    }

    @Test
    public void get_null_tickets() throws IOException {
        Mockito.when(jiraWebAPI.getTickets(any(),any()).execute().body()).thenReturn(null);
        TicketScanner ticketScanner = new TicketScanner(jiraWebAPI, "");
        Iterable<Ticket> tickets = ticketScanner.getTickets();

        int count = 0;
        for (Ticket ticket : tickets){
            count++;
        }
        assertEquals(0,count);
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTickets(any(),any());
    }

    @Test
    public void get_bad_action_tickets() throws IOException {
        String json = "{\"sections\":[{\"label\":\"History Search\",\"sub\":\"Showing 1 of 1 matching issues\",\"id\":\"hs\",\"issues\":[{\"id\":10037,\"key\":\"NSU-38\",\"keyHtml\":\"NSU-38\",\"img\":\"/rest/api/2/universal_avatar/view/type/issuetype/avatar/10300?size=medium\",\"summary\":\"MEOW|USERNAME|RESOURCE\",\"summaryText\":\"DISABLE |USERNAME|RESOURCE\"}]}]}";
        TicketSearchResponse response = gson.fromJson(json, TicketSearchResponse.class);

        Mockito.when(jiraWebAPI.getTickets(any(),any()).execute().body()).thenReturn(response);
        TicketScanner ticketScanner = new TicketScanner(jiraWebAPI, "");
        Iterable<Ticket> tickets = ticketScanner.getTickets();

        int count = 0;
        for (Ticket ticket : tickets){
            count++;
        }
        assertEquals(0,count);
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTickets(any(),any());
    }

    @Test
    public void get_bad_bad_format_tickets() throws IOException {
        String json = "{\"sections\":[{\"label\":\"History Search\",\"sub\":\"Showing 1 of 1 matching issues\",\"id\":\"hs\",\"issues\":[{\"id\":10037,\"key\":\"NSU-38\",\"keyHtml\":\"NSU-38\",\"img\":\"/rest/api/2/universal_avatar/view/type/issuetype/avatar/10300?size=medium\",\"summary\":\"MEOWUSERNAMERESOURCE\",\"summaryText\":\"DISABLE |USERNAME|RESOURCE\"}]}]}";
        TicketSearchResponse response = gson.fromJson(json, TicketSearchResponse.class);

        Mockito.when(jiraWebAPI.getTickets(any(),any()).execute().body()).thenReturn(response);
        TicketScanner ticketScanner = new TicketScanner(jiraWebAPI, "");
        Iterable<Ticket> tickets = ticketScanner.getTickets();

        int count = 0;
        for (Ticket ticket : tickets){
            count++;
        }
        assertEquals(0,count);
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTickets(any(),any());

    }

    @Test
    public void get_disable_username_tickets() throws IOException {
        String json = "{\"sections\":[{\"label\":\"History Search\",\"sub\":\"Showing 1 of 1 matching issues\",\"id\":\"hs\",\"issues\":[{\"id\":10037,\"key\":\"NSU-38\",\"keyHtml\":\"NSU-38\",\"img\":\"/rest/api/2/universal_avatar/view/type/issuetype/avatar/10300?size=medium\",\"summary\":\"DISABLE|USERNAME\",\"summaryText\":\"DISABLE |USERNAME\"}]}]}";
        TicketSearchResponse response = gson.fromJson(json, TicketSearchResponse.class);

        Mockito.when(jiraWebAPI.getTickets(any(),any()).execute().body()).thenReturn(response);
        TicketScanner ticketScanner = new TicketScanner(jiraWebAPI, "");
        Iterable<Ticket> tickets = ticketScanner.getTickets();

        int count = 0;
        for (Ticket ticket : tickets){
            assertEquals("USERNAME",ticket.getUserName());
            assertNull(ticket.getResourceName());
            assertEquals("NSU-38",ticket.getKey());
            assertEquals(Ticket.Action.DISABLE,ticket.getAction());
            count++;
        }
        assertEquals(1,count);
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTickets(any(),any());
    }

    @Test
    public void get_enable_username_tickets() throws IOException {
        String json = "{\"sections\":[{\"label\":\"History Search\",\"sub\":\"Showing 1 of 1 matching issues\",\"id\":\"hs\",\"issues\":[{\"id\":10037,\"key\":\"NSU-38\",\"keyHtml\":\"NSU-38\",\"img\":\"/rest/api/2/universal_avatar/view/type/issuetype/avatar/10300?size=medium\",\"summary\":\"ENABLE|USERNAME\",\"summaryText\":\"DISABLE |USERNAME\"}]}]}";
        TicketSearchResponse response = gson.fromJson(json, TicketSearchResponse.class);

        Mockito.when(jiraWebAPI.getTickets(any(),any()).execute().body()).thenReturn(response);
        TicketScanner ticketScanner = new TicketScanner(jiraWebAPI, "");
        Iterable<Ticket> tickets = ticketScanner.getTickets();

        int count = 0;
        for (Ticket ticket : tickets){
            assertEquals("USERNAME",ticket.getUserName());
            assertNull(ticket.getResourceName());
            assertEquals("NSU-38",ticket.getKey());
            assertEquals(Ticket.Action.ENABLE,ticket.getAction());
            count++;
        }
        assertEquals(1,count);
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTickets(any(),any());
    }

}