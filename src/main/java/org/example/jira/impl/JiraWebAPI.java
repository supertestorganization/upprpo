package org.example.jira.impl;

import org.example.jira.impl.model.ticket.TicketSearchResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface JiraWebAPI {
    @Headers({"Accept: application/json"})
    @GET("issue/picker?currentJQL=status = \"In Progress\" AND type=\"Midpoint\"")
    Call<TicketSearchResponse> getTickets(
            @Header("Authorization") String authHeader
    );
}
