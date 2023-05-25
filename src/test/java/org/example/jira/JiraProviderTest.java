package org.example.jira;

import com.google.gson.Gson;
import okhttp3.ResponseBody;
import org.example.application.fabric.JiraConfig;
import org.example.jira.impl.JiraProviderImpl;
import org.example.jira.impl.JiraWebAPI;
import org.example.jira.impl.model.ticket.TicketSearchResponse;
import org.example.jira.impl.model.transition.TransitionSearchResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class JiraProviderTest {

    JiraProvider jiraProvider;
    JiraWebAPI jiraWebAPI;
    retrofit2.Response<ResponseBody> mockedResponse;


    Gson gson = new Gson();

    @Before
    public void init() throws IOException{
        jiraWebAPI = Mockito.mock(JiraWebAPI.class, Answers.RETURNS_DEEP_STUBS);
        JiraConfig.loadConfig();


        String json = "{\"sections\":[{\"label\":\"History Search\",\"sub\":\"Showing 1 of 1 matching issues\",\"id\":\"hs\",\"issues\":[{\"id\":10037,\"key\":\"NSU-38\",\"keyHtml\":\"NSU-38\",\"img\":\"/rest/api/2/universal_avatar/view/type/issuetype/avatar/10300?size=medium\",\"summary\":\"DISABLE |USERNAME|RESOURCE\",\"summaryText\":\"DISABLE |USERNAME|RESOURCE\"}]}]}";
        TicketSearchResponse getTicketResponse = gson.fromJson(json, TicketSearchResponse.class);
        Mockito.when(jiraWebAPI.getTickets(any(),any()).execute().body()).thenReturn(getTicketResponse);

        json = "{\"expand\":\"transitions\",\"transitions\":[{\"id\":\"11\",\"name\":\"To Do\",\"to\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/status/10000\",\"description\":\"\",\"iconUrl\":\"https://teamnsu.atlassian.net/\",\"name\":\"To Do\",\"id\":\"10000\",\"statusCategory\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/statuscategory/2\",\"id\":2,\"key\":\"new\",\"colorName\":\"blue-gray\",\"name\":\"To Do\"}},\"hasScreen\":false,\"isGlobal\":true,\"isInitial\":false,\"isAvailable\":true,\"isConditional\":false,\"fields\":{},\"isLooped\":false},{\"id\":\"21\",\"name\":\"In Progress\",\"to\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/status/10001\",\"description\":\"\",\"iconUrl\":\"https://teamnsu.atlassian.net/\",\"name\":\"In Progress\",\"id\":\"10001\",\"statusCategory\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/statuscategory/4\",\"id\":4,\"key\":\"indeterminate\",\"colorName\":\"yellow\",\"name\":\"In Progress\"}},\"hasScreen\":false,\"isGlobal\":true,\"isInitial\":false,\"isAvailable\":true,\"isConditional\":false,\"fields\":{},\"isLooped\":false},{\"id\":\"2\",\"name\":\"Failed\",\"to\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/status/10003\",\"description\":\"\",\"iconUrl\":\"https://teamnsu.atlassian.net/\",\"name\":\"failed\",\"id\":\"10003\",\"statusCategory\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/statuscategory/3\",\"id\":3,\"key\":\"done\",\"colorName\":\"green\",\"name\":\"Done\"}},\"hasScreen\":false,\"isGlobal\":false,\"isInitial\":false,\"isAvailable\":true,\"isConditional\":false,\"fields\":{},\"isLooped\":false},{\"id\":\"3\",\"name\":\"Done\",\"to\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/status/10002\",\"description\":\"\",\"iconUrl\":\"https://teamnsu.atlassian.net/\",\"name\":\"Done\",\"id\":\"10002\",\"statusCategory\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/statuscategory/3\",\"id\":3,\"key\":\"done\",\"colorName\":\"green\",\"name\":\"Done\"}},\"hasScreen\":false,\"isGlobal\":false,\"isInitial\":false,\"isAvailable\":true,\"isConditional\":false,\"fields\":{},\"isLooped\":false}]}";
        TransitionSearchResponse transitionSearchResponse = gson.fromJson(json, TransitionSearchResponse.class);
        Mockito.when(jiraWebAPI.getTransitions(any(),any()).execute().body()).thenReturn(transitionSearchResponse);

        mockedResponse = Mockito.mock(retrofit2.Response.class);

        Mockito.when(jiraWebAPI.applyTransition(any(),any(),any()).execute()).thenReturn(mockedResponse);
        Mockito.when(jiraWebAPI.setDescription(any(),any(),any()).execute()).thenReturn(mockedResponse);


        jiraProvider = new JiraProviderImpl(jiraWebAPI, "dummy token");
    }

    @Test
    public void get_tickets() throws IOException {
        Iterable<Ticket> tickets = jiraProvider.getTickets();

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
    public void make_ticket_done_ok() throws IOException {
        Mockito.when(mockedResponse.code()).thenReturn(200);
        jiraProvider.makeTicketDone("NSU-38");
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTransitions(any(), any());
        Mockito.verify(jiraWebAPI, Mockito.atLeast(2)).applyTransition(any(),any(), any());
        Mockito.verify(mockedResponse, Mockito.times(1)).code();

    }

    @Test
    public void make_ticket_failed_ok() throws IOException {
        Mockito.when(mockedResponse.code()).thenReturn(200);
        jiraProvider.makeTicketFailed("NSU-38");
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTransitions(any(),any());
        Mockito.verify(jiraWebAPI, Mockito.atLeast(2)).applyTransition(any(),any(), any());
        Mockito.verify(mockedResponse, Mockito.times(1)).code();
    }

    @Test
    public void set_ticket_description_ok() throws IOException {
        Mockito.when(mockedResponse.code()).thenReturn(200);
        jiraProvider.setTicketDescription("NSU-38", "test desc");
        Mockito.verify(jiraWebAPI, Mockito.times(2)).setDescription(any(),any(),any());
        Mockito.verify(mockedResponse, Mockito.times(1)).code();
    }

    @Test
    public void make_ticket_done_bad_code() throws IOException {
        Mockito.when(mockedResponse.code()).thenReturn(400);

        assertThrows(IOException.class, () -> jiraProvider.makeTicketDone("NSU-38"));

        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTransitions(any(), any());
        Mockito.verify(jiraWebAPI, Mockito.atLeast(2)).applyTransition(any(),any(), any());
        Mockito.verify(mockedResponse, Mockito.times(2)).code();
    }

    @Test
    public void make_ticket_failed_bad_code() throws IOException {
        Mockito.when(mockedResponse.code()).thenReturn(400);

        assertThrows(IOException.class, () -> jiraProvider.makeTicketFailed("NSU-38"));

        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTransitions(any(),any());
        Mockito.verify(jiraWebAPI, Mockito.atLeast(2)).applyTransition(any(),any(), any());
        Mockito.verify(mockedResponse, Mockito.times(2)).code();
    }

    @Test
    public void set_ticket_description_bad_code() throws IOException {
        Mockito.when(mockedResponse.code()).thenReturn(400);

        assertThrows(IOException.class, () -> jiraProvider.setTicketDescription("NSU-38", "test desc"));

        Mockito.verify(jiraWebAPI, Mockito.times(2)).setDescription(any(),any(),any());
        Mockito.verify(mockedResponse, Mockito.times(2)).code();
    }
}