package org.example.midpoint;

import okhttp3.Credentials;
import org.example.application.fabric.Config;
import org.example.midpoint.models.GetMidpointResourcesResponse;
import org.example.midpoint.models.GetMidpointUserResponse;
import org.example.midpoint.models.MidpointResource;
import org.example.midpoint.models.MidpointUser;
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
    public OperationResult activateAccount(String userName, String resourceName) throws IOException {
      /*  String resourceOid = getResourceOid(resourceName);
        if (resourceOid == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "Resource not found");
        }
       */
        List<MidpointUser> users = getUsers().getObj().getMidpointObjectsList();
        String userOid = findUserOid(userName, users);

        return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "");
    }

    @Override
    public OperationResult disableAccount(String userName, String resourceName) throws IOException {
        //TODO: implement
        return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "");
    }


    @Override
    public OperationResult disableUser(String userName) throws IOException {
        String userOid = getUserOid(userName);
        if (userOid == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "User not found");
        }
        midpointWebAPI.postChangeUserActivation(authToken, userOid, disableUserBody);
        return new OperationResult(OperationResult.OPERATION_STATUS.SUCCEED, "User disabled");
    }

    @Override
    public OperationResult activateUser(String userName) throws IOException {
        String userOid = getUserOid(userName);
        if (userOid == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "User not found");
        }
        midpointWebAPI.postChangeUserActivation(authToken, userOid, enableUserBody);
        return new OperationResult(OperationResult.OPERATION_STATUS.SUCCEED, "User enabled");
    }

    private String getUserOid(String name) throws IOException {
        List<MidpointUser> userList = getUsers().getObj().getMidpointObjectsList();
        return findUserOid(name, userList);
    }

    private String getResourceOid(String name) throws IOException {
        List<MidpointResource> resourcesList = getResources().getObjRes().getMidpointResources();
        return findResourceOid(name, resourcesList);
    }


    private GetMidpointUserResponse getUsers() throws IOException {

        return midpointWebAPI.getUsers(authToken).execute().body();
    }

    private GetMidpointResourcesResponse getResources() throws IOException {
        return midpointWebAPI.getResources(authToken).execute().body();
    }

    private String findUserOid(String name, List<MidpointUser> userList) {
        for (MidpointUser midpointUser : userList) {
            if (midpointUser.getName().equals(name)) {
                return midpointUser.getOid();
            }
        }
            return null;
    }

    private String findResourceOid(String name, List<MidpointResource> resourceList) {
        for (MidpointResource midpointResource : resourceList) {
            if (midpointResource.getResourceName().equals(name)) {
                return midpointResource.getResourceOid();
            }
        }
        return null;
    }

}
