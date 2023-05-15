package org.example.midpoint;

import org.example.midpoint.models.GetMidpointResourcesResponse;
import org.example.midpoint.models.GetMidpointUserResponse;
import org.example.midpoint.models.PostPropertyResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface MidpointWebAPI {

    @Headers({"Accept: application/json"})
    @GET("users")
    Call<GetMidpointUserResponse> getUsers(
            @Header("Authorization") String authHeader
    );

    @Headers({"Accept: application/json"})
    @GET("resources")
    Call<GetMidpointResourcesResponse> getResources(
        @Header("Authorization") String authHeader
    );

    @Headers({"Accept: application/json"})
    @POST("users/{oid}")
    Call<PostPropertyResponse> postChangeUserActivation (
            @Header("Authorization") String authHeader,
            @Path("oid") String userOid,
            @Body String body
    );
}
