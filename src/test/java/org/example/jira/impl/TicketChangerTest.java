package org.example.jira.impl;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.example.application.fabric.JiraConfig;
import org.example.jira.impl.model.transition.TransitionSearchResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class TicketChangerTest {
    private JiraWebAPI jiraWebAPI;
    private TicketChanger ticketChanger;
    private final Gson gson = new Gson();

    @Before
    public void mock_jira() throws IOException {
        jiraWebAPI = Mockito.mock(JiraWebAPI.class, Answers.RETURNS_DEEP_STUBS);
        String json = "{\"expand\":\"transitions\",\"transitions\":[{\"id\":\"11\",\"name\":\"To Do\",\"to\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/status/10000\",\"description\":\"\",\"iconUrl\":\"https://teamnsu.atlassian.net/\",\"name\":\"To Do\",\"id\":\"10000\",\"statusCategory\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/statuscategory/2\",\"id\":2,\"key\":\"new\",\"colorName\":\"blue-gray\",\"name\":\"To Do\"}},\"hasScreen\":false,\"isGlobal\":true,\"isInitial\":false,\"isAvailable\":true,\"isConditional\":false,\"fields\":{},\"isLooped\":false},{\"id\":\"21\",\"name\":\"In Progress\",\"to\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/status/10001\",\"description\":\"\",\"iconUrl\":\"https://teamnsu.atlassian.net/\",\"name\":\"In Progress\",\"id\":\"10001\",\"statusCategory\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/statuscategory/4\",\"id\":4,\"key\":\"indeterminate\",\"colorName\":\"yellow\",\"name\":\"In Progress\"}},\"hasScreen\":false,\"isGlobal\":true,\"isInitial\":false,\"isAvailable\":true,\"isConditional\":false,\"fields\":{},\"isLooped\":false},{\"id\":\"2\",\"name\":\"Failed\",\"to\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/status/10003\",\"description\":\"\",\"iconUrl\":\"https://teamnsu.atlassian.net/\",\"name\":\"failed\",\"id\":\"10003\",\"statusCategory\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/statuscategory/3\",\"id\":3,\"key\":\"done\",\"colorName\":\"green\",\"name\":\"Done\"}},\"hasScreen\":false,\"isGlobal\":false,\"isInitial\":false,\"isAvailable\":true,\"isConditional\":false,\"fields\":{},\"isLooped\":false},{\"id\":\"3\",\"name\":\"Done\",\"to\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/status/10002\",\"description\":\"\",\"iconUrl\":\"https://teamnsu.atlassian.net/\",\"name\":\"Done\",\"id\":\"10002\",\"statusCategory\":{\"self\":\"https://teamnsu.atlassian.net/rest/api/3/statuscategory/3\",\"id\":3,\"key\":\"done\",\"colorName\":\"green\",\"name\":\"Done\"}},\"hasScreen\":false,\"isGlobal\":false,\"isInitial\":false,\"isAvailable\":true,\"isConditional\":false,\"fields\":{},\"isLooped\":false}]}";
        TransitionSearchResponse response = gson.fromJson(json, TransitionSearchResponse.class);

        Mockito.when(jiraWebAPI.getTransitions(any(),any()).execute().body()).thenReturn(response);

        retrofit2.Response<ResponseBody> response1 = Mockito.mock(retrofit2.Response.class);
        Mockito.when(jiraWebAPI.applyTransition(any(),any(),any()).execute()).thenReturn(response1);

        Mockito.when(jiraWebAPI.setDescription(any(),any(),any()).execute()).thenReturn(response1);
        ticketChanger = new TicketChanger(jiraWebAPI, "");

        JiraConfig.loadConfig();
    }

    @Test
    public void set_ticket_description() throws IOException {
        ticketChanger.setTicketDescription("NSU-38", "test desc");
        Mockito.verify(jiraWebAPI, Mockito.times(2)).setDescription(any(),any(),any());
    }

    @Test
    public void make_ticket_done() throws IOException {
        ticketChanger.makeTicketDone("NSU-38");
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTransitions(any(), any());
        Mockito.verify(jiraWebAPI, Mockito.atLeast(2)).applyTransition(any(),any(), any());

        ticketChanger.makeTicketDone("NSU-38");

        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTransitions(any(), any());
        Mockito.verify(jiraWebAPI, Mockito.atLeast(3)).applyTransition(any(),any(), any());
    }

    @Test
    public void make_ticket_failed() throws IOException {
        ticketChanger.makeTicketFailed("NSU-38");
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTransitions(any(),any());
        Mockito.verify(jiraWebAPI, Mockito.atLeast(2)).applyTransition(any(),any(), any());

        ticketChanger.makeTicketFailed("NSU-38");
        Mockito.verify(jiraWebAPI, Mockito.times(2)).getTransitions(any(), any());
        Mockito.verify(jiraWebAPI, Mockito.atLeast(3)).applyTransition(any(),any(), any());
    }
}