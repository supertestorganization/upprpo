package org.example.application.fabric;

import okhttp3.Credentials;
import org.example.jira.JiraProvider;
import org.example.jira.impl.JiraProviderImpl;
import org.example.jira.impl.JiraWebAPI;
import org.example.midpoint.MidpointProvider;
import org.example.midpoint.MidpointProviderImpl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FabricImpl implements Fabric{

    @Override
    public JiraProvider getJiraProvider() {
        String token = JiraConfig.API_TOKEN;
        String username = JiraConfig.LOGIN;
        String authToken = Credentials.basic(username, token);

        String baseUrl = JiraConfig.BASE_URL;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        JiraWebAPI jiraWebAPI = retrofit.create(JiraWebAPI.class);

        return new JiraProviderImpl(jiraWebAPI, authToken);
    }

    @Override
    public MidpointProvider getMidpointProvider() {
        return new MidpointProviderImpl();
    }
}
