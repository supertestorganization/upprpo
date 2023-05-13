package org.example.jira.impl;

import okhttp3.Credentials;
import org.example.jira.JiraProvider;
import org.example.jira.Ticket;
import org.example.jira.impl.model.ticket.TicketImpl;
import org.example.jira.impl.model.ticket.internal.Issue;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;

public class JiraProviderImpl implements JiraProvider {

    private final String authToken;
    private final Retrofit retrofit;
    private final JiraWebAPI jiraWebAPI;

    private String username = "d.bondar1@g.nsu.ru";
    private String token = "ATATT3xFfGF0bfMkk5JFvMRtN63Vx7kdQ3acbqDJCD1p5i6oEFUA68WZql_HK6cqvQ9CGPjkgsLYTDzxN_U3UHoXOGF_IVObS_Ghnag-d913vF4MD3vdNaKePnwebsX267aKySOkmRvOUf84cDsHFF2-tqVhh8DUy4bsPxOhE76LuIY--OFfJsc=CA92AAF8";
    private String baseUrl = "https://teamnsu.atlassian.net/rest/api/3/";

    public JiraProviderImpl() {
        authToken = Credentials.basic(username, token);
        this.retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        this.jiraWebAPI = retrofit.create(JiraWebAPI.class);
    }

    @Override
    public Iterable<Ticket> getTickets() throws IOException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        Issue[] issues = jiraWebAPI.getTickets(authToken).execute().body().getSections()[0].getIssues();
        for (Issue issue : issues) {
            String[] tokens = issue.getSummary().split("\\|");
            if (tokens.length < 2 || tokens.length > 3) {
                continue;
            }

            Ticket.Action action;
            if (tokens[0].equals("ENABLE")) {
                action = Ticket.Action.ENABLE;
            } else if (tokens[0].equals("DISABLE")) {
                action = Ticket.Action.DISABLE;
            } else {
                continue;
            }

            if (tokens.length == 2) {
                tickets.add(new TicketImpl(issue.getKey(), tokens[1], null, action));
            } else {
                tickets.add(new TicketImpl(issue.getKey(), tokens[1], tokens[2], action));
            }
        }
        return tickets;
    }

    @Override
    public void makeTicketDone(String ticketKey) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void makeTicketFailed(String ticketKey) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeTicketDescription(String ticketKey, String newDescription) throws IOException {
        throw new UnsupportedOperationException();
    }
}
