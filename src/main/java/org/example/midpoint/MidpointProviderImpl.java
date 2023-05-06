package org.example.midpoint;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.example.midpoint.exceptions.BadResource;
import org.example.midpoint.exceptions.BadUser;
import org.example.midpoint.models.GetUsersResponse;
import org.example.midpoint.models.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MidpointProviderImpl implements MidpointProvider {
    private final Retrofit retrofit;
    private final MidpointWebAPI midpointWebAPI;
    private static final String BASE_URL = "http://localhost:8080/midpoint/ws/rest/";

    public MidpointProviderImpl() {

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.midpointWebAPI = retrofit.create(MidpointWebAPI.class);
    }

    @Override
    public void activateUser(String userName, String resourceName) throws BadResource, BadUser, IOException {
        String userOid = getUserOid(userName);
                
    }

    @Override
    public void disableUser(String userName, String resourceName) throws BadResource, BadUser, IOException {

    }

    private String getUserOid(String name) throws IOException {
        List<User> userList = getUsers().getObj().getUserList();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                return user.getOid();
            }
        }
        return null;
    }


    private GetUsersResponse getUsers() throws IOException {
        var authToken = Credentials.basic("administrator", "5ecr3t");
        return midpointWebAPI.getUsers(authToken).execute().body();
    }


}
