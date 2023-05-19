package org.example.jira.impl;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.example.application.fabric.JiraConfig;
import org.example.jira.impl.model.transition.Transition;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

class TicketChanger {

    private final JiraWebAPI jiraWebAPI;
    private final String authToken;
    private final MediaType mediaType= MediaType.parse("application/json; charset=utf-8");

    private boolean hasTransactions = false;
    private int doneId;
    private int failedId;

    public TicketChanger(JiraWebAPI jiraWebAPI, String authToken){
        this.jiraWebAPI = jiraWebAPI;
        this.authToken = authToken;
    }

    public Response<ResponseBody> setTicketDescription(String ticketKey, String desc) throws IOException {
        String jsonBody = String.format(JiraConfig.DESCRIPTION_TEMPLATE, desc);
        return jiraWebAPI.setDescription(authToken,ticketKey, RequestBody.create(mediaType,jsonBody)).execute();
    }
    public Response<ResponseBody> makeTicketDone(String ticketKey) throws IOException {
        if (!hasTransactions){
            receiveTransitions(ticketKey);
            hasTransactions = true;
        }

        String jsonBody = String.format(JiraConfig.TRANSITION_TEMPLATE, doneId);
        return jiraWebAPI.applyTransition(authToken,ticketKey,RequestBody.create(mediaType,jsonBody)).execute();
    }

    public Response<ResponseBody> makeTicketFailed(String ticketKey) throws IOException {
        if (!hasTransactions){
            receiveTransitions(ticketKey);
            hasTransactions = true;
        }

        String jsonBody = String.format(JiraConfig.TRANSITION_TEMPLATE, failedId);
        return jiraWebAPI.applyTransition(authToken,ticketKey,RequestBody.create(mediaType,jsonBody)).execute();
    }

    private void receiveTransitions(String issueKey) throws IOException{
        Transition[] transitions;
        try {
            transitions = Objects.requireNonNull(jiraWebAPI.getTransitions(authToken, issueKey).execute().body()).getTransitions();
        } catch (NullPointerException e){
            return;
        }

        for (Transition transition : transitions){
            if ("Failed".equals(transition.getName())){
                failedId = Integer.parseInt(Objects.requireNonNull(transition.getId()));
            } else if ("Done".equals(transition.getName())){
                doneId = Integer.parseInt(Objects.requireNonNull(transition.getId()));
            }
        }

    }
}
