package org.example.jira.impl;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.example.jira.impl.model.ticket.TicketSearchResponse;
import org.example.jira.impl.model.transition.TransitionSearchResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface JiraWebAPI {
    @Headers({"Accept: application/json"})
    @GET("issue/picker")
    Call<TicketSearchResponse> getTickets(
            @Header("Authorization") String authHeader,
            @Query("currentJQL") String jql
    );

    @Headers({"Accept: application/json"})
    @GET("issue/{issueKey}/transitions?expand=transitions.fields")
    Call<TransitionSearchResponse> getTransitions(
            @Header("Authorization") String authHeader,
            @Path("issueKey") String issueKey
    );

    @Headers({"Accept: application/json"})
    @POST("issue/{issueKey}/transitions")
    Call<ResponseBody> applyTransition(
            @Header("Authorization") String authHeader,
            @Path("issueKey") String issueKey,
            @Body RequestBody jsonBody
    );

    @Headers({"Accept: application/json"})
    @PUT("issue/{issueKey}/")
    Call<ResponseBody> setDescription(
            @Header("Authorization") String authHeader,
            @Path("issueKey") String issueKey,
            @Body RequestBody jsonBody
    );

}
