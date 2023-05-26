package org.example.midpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.Credentials;
import okhttp3.RequestBody;
import org.example.application.fabric.MidpointConfig;
import org.example.midpoint.models.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MidpointProviderImpl implements MidpointProvider {
    Retrofit retrofit;
    private final MidpointWebAPI midpointWebAPI;
    private static final String baseUrl = MidpointConfig.MIDPOINT_BASE_URL;
    private final String enableUserBody = MidpointConfig.ENABLE_USER_BODY;
    private final String disableUserBody = MidpointConfig.DISABLE_USER_BODY;
    private final String enableAccountBody = MidpointConfig.ENABLE_ACCOUNT_BODY;
    private final String disableAccountBody = MidpointConfig.DISABLE_ACCOUNT_BODY;
    private final String authToken;
    private final String noUserFail = "User not found";
    private final String noResourceFail = "Resource not found";
    private final String mediaFormat = "application/json; charset=utf-8";

    public MidpointProviderImpl() {
        Type assignmentType = new TypeToken<List<Assignment>>() {}.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(assignmentType, new AssignmentsAdapter()).create();
        this.retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson)).build();
        this.midpointWebAPI = retrofit.create(MidpointWebAPI.class);
        authToken = Credentials.basic(MidpointConfig.MIDPOINT_LOGIN, MidpointConfig.MIDPOINT_PASSWORD);
    }

    @Override
    public OperationResult activateAccount(String userName, String resourceName) throws IOException {

        MidpointUser user = getUser(userName);
        if (user == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, noUserFail);
        }
        MidpointResource resource = getResourceByName(resourceName);
        if (resource == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, noResourceFail);
        }
        if (user.getAssignmentList() == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "this user has no accounts");
        }
        return changeAccountActivation(user, resource, enableAccountBody);
    }

    @Override
    public OperationResult disableAccount(String userName, String resourceName) throws IOException {
        MidpointUser user = getUser(userName);
        if (user == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, noUserFail);
        }
        MidpointResource resource = getResourceByName(resourceName);
        if (resource == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, noResourceFail);
        }
        if (user.getAssignmentList() == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "this user has no accounts");
        }
        return changeAccountActivation(user, resource, disableAccountBody);
    }

    private OperationResult changeAccountActivation(MidpointUser user, MidpointResource resource, String disableAccountBody) throws IOException {
        Integer assignmentId = getAssignmentId(resource.getResourceOid(), user.getAssignmentList());
        if (assignmentId == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "User %s does not have assignment for resource %s".formatted(user.getName(), resource.getResourceName()));
        }
        var body = RequestBody.create(okhttp3.MediaType.parse(mediaFormat), disableAccountBody.formatted(assignmentId));
        midpointWebAPI.postChangeUser(authToken, user.getOid(), body).execute();
        return new OperationResult(OperationResult.OPERATION_STATUS.SUCCEED, "Activation of account in %s of user %s is changed".formatted(resource.getResourceName(), user.getName()));
    }


    @Override
    public OperationResult disableUser(String userName) throws IOException {
        return changeUserActivation(userName, disableUserBody);
    }

    @Override
    public OperationResult activateUser(String userName) throws IOException {
        return changeUserActivation(userName, enableUserBody);
    }

    private OperationResult changeUserActivation(String userName, String enableUserBody) throws IOException {
        MidpointUser user = getUser(userName);
        if (user == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, noUserFail);
        }
        var body = RequestBody.create(okhttp3.MediaType.parse(mediaFormat), enableUserBody);
        midpointWebAPI.postChangeUser(authToken, user.getOid(), body).execute();
        return  new OperationResult(OperationResult.OPERATION_STATUS.SUCCEED, "User activation changed");
    }

    private MidpointUser getUser(String name) throws IOException {
        List<MidpointUser> userList = getUsers().getObj().getMidpointObjectsList();
        return findUserByName(name, userList);
    }

    private MidpointResource getResourceByName(String name) throws IOException {
        List<MidpointResource> resourcesList = getResources().getObjRes().getMidpointResources();
        return findResource(name, resourcesList);
    }


    private GetMidpointUserResponse getUsers() throws IOException {

        return midpointWebAPI.getUsers(authToken).execute().body();
    }

    private GetMidpointResourcesResponse getResources() throws IOException {
        return midpointWebAPI.getResources(authToken).execute().body();
    }

    private MidpointUser findUserByName(String name, List<MidpointUser> userList) {
        for (MidpointUser midpointUser : userList) {
            if (midpointUser.getName().replace(" ", "").equals(name)) {
                return midpointUser;
            }
        }
            return null;
    }

    private MidpointResource findResource(String name, List<MidpointResource> resourceList) {
        for (MidpointResource midpointResource : resourceList) {
            if (midpointResource.getResourceName().replace(" ", "").equals(name)) {
                return midpointResource;
            }
        }
        return null;
    }

    private Integer getAssignmentId(String resourceOid, List<Assignment> assignments) {
        for (Assignment assignment: assignments) {
            if (assignment.getConstruction().getResourceRef().getOid().equals(resourceOid)) {
                return assignment.getId();
            }
        }
        return null;
    }

}
