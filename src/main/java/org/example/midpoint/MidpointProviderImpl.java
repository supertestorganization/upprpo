package org.example.midpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private final String baseUrl = MidpointConfig.MIDPOINT_BASE_URL;
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
        Integer assignmentId = getAssignmentId(resource.getResourceOid(), user.getAssignmentList());
        if (assignmentId == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "User %s does not have assignment for resource %s".formatted(userName, resourceName));
        }
        var body = RequestBody.create(okhttp3.MediaType.parse(mediaFormat), enableAccountBody.formatted(assignmentId));
        midpointWebAPI.postChangeActivation(authToken, user.getOid(), body).execute();
        return new OperationResult(OperationResult.OPERATION_STATUS.SUCCEED, "Account in %s of user %s is enabled".formatted(resourceName, userName));
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
        Integer assignmentId = getAssignmentId(resource.getResourceOid(), user.getAssignmentList());
        if (assignmentId == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, "User %s does not have assignment for resource %s".formatted(userName, resourceName));
        }
        var body = RequestBody.create(okhttp3.MediaType.parse(mediaFormat), disableAccountBody.formatted(assignmentId));
        midpointWebAPI.postChangeActivation(authToken, user.getOid(), body).execute();
        return new OperationResult(OperationResult.OPERATION_STATUS.SUCCEED, "Account in %s of user %s is disabled".formatted(resourceName, userName));
    }




    @Override
    public OperationResult disableUser(String userName) throws IOException {
        MidpointUser user = getUser(userName);
        if (user == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, noUserFail);
        }
        var body = RequestBody.create(okhttp3.MediaType.parse(mediaFormat), disableUserBody);
        midpointWebAPI.postChangeActivation(authToken, user.getOid(), body).execute();
        return new OperationResult(OperationResult.OPERATION_STATUS.SUCCEED, "User disabled");
    }

    @Override
    public OperationResult activateUser(String userName) throws IOException {
        MidpointUser user = getUser(userName);
        if (user == null) {
            return new OperationResult(OperationResult.OPERATION_STATUS.FAILED, noUserFail);
        }
        var body = RequestBody.create(okhttp3.MediaType.parse(mediaFormat), enableUserBody);
        midpointWebAPI.postChangeActivation(authToken, user.getOid(), body).execute();
        return new OperationResult(OperationResult.OPERATION_STATUS.SUCCEED, "User enabled");
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
            if (midpointUser.getName().equals(name)) {
                return midpointUser;
            }
        }
            return null;
    }

    private MidpointResource findResource(String name, List<MidpointResource> resourceList) {
        for (MidpointResource midpointResource : resourceList) {
            if (midpointResource.getResourceName().equals(name)) {
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
