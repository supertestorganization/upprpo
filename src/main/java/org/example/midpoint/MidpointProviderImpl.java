package org.example.midpoint;

import okhttp3.Credentials;
import org.example.application.fabric.Config;
import org.example.midpoint.exceptions.BadResource;
import org.example.midpoint.exceptions.BadUser;
import org.example.midpoint.models.GetMidpointObjectsResponse;
import org.example.midpoint.models.MidpointObject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class MidpointProviderImpl implements MidpointProvider {
    private final Retrofit retrofit;
    private final MidpointWebAPI midpointWebAPI;
    private final String baseUrl = org.example.application.fabric.Config.MIDPOINT_BASE_URL;
    private final String enableUserBody = Config.ENABLE_USER_BODY;
    private final String disableUserBody = Config.DISABLE_USER_BODY;
    private final String authToken;

    public MidpointProviderImpl() {
        this.retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        this.midpointWebAPI = retrofit.create(MidpointWebAPI.class);
        authToken = Credentials.basic(Config.MIDPOINT_LOGIN, Config.MIDPOINT_PASSWORD);
    }

    @Override
    public void enableAccount(String userName, String resourceName) throws BadResource, BadUser, IOException {
        String resourceOid = getResourceOid(resourceName);
        if (resourceOid == null) {
            throw new BadResource();
        }

    }

    @Override
    public void disableAccount(String userName, String resourceName) throws BadResource, BadUser, IOException {

    }


    @Override
    public void disableUser(String userName) throws BadUser, IOException {
        String userOid = getUserOid(userName);
        if (userOid == null) {
            throw new BadUser();
        }
        midpointWebAPI.postChangeUserActivation(authToken, userOid, disableUserBody);
    }

    @Override
    public void enableUser(String userName) throws BadUser, IOException {
        String userOid = getUserOid(userName);
        if (userOid == null) {
            throw new BadUser();
        }
        midpointWebAPI.postChangeUserActivation(authToken, userOid, enableUserBody);
    }

    private String getUserOid(String name) throws IOException {
        List<MidpointObject> userList = getUsers().getObj().getMidpointObjectsList();
        return findOid(name, userList);
    }

    private String getResourceOid(String name) throws IOException {
        List<MidpointObject> resourcesList = getResources().getObj().getMidpointObjectsList();
        return findOid(name, resourcesList);
    }


    private GetMidpointObjectsResponse getUsers() throws IOException {

        return midpointWebAPI.getUsers(authToken).execute().body();
    }

    private GetMidpointObjectsResponse getResources() throws IOException {
        return midpointWebAPI.getResources(authToken).execute().body();
    }

    private String findOid(String name, List<MidpointObject> objectList) {
        for (MidpointObject midpointObject: objectList) {
            if (midpointObject.getName().equals(name)) {
                return midpointObject.getOid();
            }
        }
            return null;
    }

}
