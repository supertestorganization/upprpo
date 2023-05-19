package org.example.jira.impl;

import okhttp3.Credentials;
import org.example.application.fabric.JiraConfig;
import org.example.jira.JiraProvider;
import org.example.jira.Ticket;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class JiraProviderImpl implements JiraProvider {

    private final TicketScanner ticketScanner;
    private final TicketChanger ticketChanger;

    public JiraProviderImpl() {
        String token = JiraConfig.API_TOKEN;
        String username = JiraConfig.LOGIN;
        String authToken = Credentials.basic(username, token);

        String baseUrl = JiraConfig.BASE_URL;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        JiraWebAPI jiraWebAPI = retrofit.create(JiraWebAPI.class);
        this.ticketScanner = new TicketScanner(jiraWebAPI, authToken);
        this.ticketChanger = new TicketChanger(jiraWebAPI, authToken);

    }

    @Override
    public Iterable<Ticket> getTickets() throws IOException {
        return ticketScanner.getTickets();
    }

    @Override
    public void makeTicketDone(String ticketKey) throws IOException {
        var response = ticketChanger.makeTicketDone(ticketKey);
        if (isBadCode(response.code())){
            throw new IOException("Bad code: " + response.code() +'\n'+ response.message());
        }
    }

    @Override
    public void makeTicketFailed(String ticketKey) throws IOException {
        var response = ticketChanger.makeTicketFailed(ticketKey);
        if (isBadCode(response.code())){
            throw new IOException("Bad code: " + response.code() +'\n'+ response.message());
        }
    }

    @Override
    public void setTicketDescription(String ticketKey, String newDescription) throws IOException {
        var response = ticketChanger.setTicketDescription(ticketKey,newDescription);
        if (isBadCode(response.code())){
            throw new IOException("Bad code: " + response.code() +'\n'+ response.message());
        }
    }

    private boolean isBadCode(int responseCode){
        return responseCode >= 300 || responseCode < 200;
    }
}
