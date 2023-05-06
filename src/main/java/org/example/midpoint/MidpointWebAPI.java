package org.example.midpoint;

import org.example.midpoint.models.GetUsersResponse;
import org.example.midpoint.models.PostPropertyResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MidpointWebAPI {

    @Headers({"Accept: application/json"})
    @GET("users")
    Call<GetUsersResponse> getUsers(
            @Header("Authorization") String authHeader
    );

    @POST("users/{oid}") {
    Call<PostPropertyResponse> postActivated
    }
}
