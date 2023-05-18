import org.example.midpoint.OperationResult;
import org.example.midpoint.MidpointProviderImpl;
import org.example.midpoint.MidpointWebAPI;
import org.example.midpoint.models.GetMidpointObjectsResponse;
import org.example.midpoint.models.MidpointObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MidpointProviderImplTest {

    @Mock
    private MidpointWebAPI midpointWebAPI;

    private MidpointProviderImpl midpointProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        midpointProvider = new MidpointProviderImpl();
        midpointProvider.setMidpointWebAPI(midpointWebAPI);
    }

    @Test
    public void testActivateUser_Success() throws IOException {
        String userName = "testUser";
        String userOid = "userOid";
        String enableUserBody = "enableUserBody";
        OperationResult expectedResult = new OperationResult(OperationResult.OPERATION_STATUS.SUCCEED, "User enabled");

        when(midpointWebAPI.postChangeUserActivation(anyString(), anyString(), anyString())).thenReturn(null);
        when(midpointProvider.getUserOid(userName)).thenReturn(userOid);
        midpointProvider.setEnableUserBody(enableUserBody);

        OperationResult result = midpointProvider.activateUser(userName);

        verify(midpointWebAPI, times(1)).postChangeUserActivation(anyString(), eq(userOid), eq(enableUserBody));
        assertEquals(expectedResult, result);
    }

    @Test
    public void testActivateUser_UserNotFound() throws IOException {
        String userName = "testUser";
        String expectedErrorMessage = "User not found";

        when(midpointProvider.getUserOid(userName)).thenReturn(null);

        OperationResult result = midpointProvider.activateUser(userName);

        assertEquals(OperationResult.OPERATION_STATUS.FAILED, result.status());
        assertEquals(expectedErrorMessage, result.msg());
    }

    @Test
    public void testGetUserOid_UserFound() throws IOException {
        String userName = "testUser";
        String expectedUserOid = "userOid";

        List<MidpointObject> userList = new ArrayList<>();
        userList.add(new MidpointObject("user1", "userOid1"));
        userList.add(new MidpointObject(userName, expectedUserOid));
        userList.add(new MidpointObject("user3", "userOid3"));

        when(midpointProvider.getUsers()).thenReturn(new GetMidpointObjectsResponse(userList));

        String result = midpointProvider.getUserOid(userName);

        assertEquals(expectedUserOid, result);
    }

    @Test
    public void testGetUserOid_UserNotFound() throws IOException {
        String userName = "testUser";

        List<MidpointObject> userList = new ArrayList<>();
        userList.add(new MidpointObject("user1", "userOid1"));
        userList.add(new MidpointObject("user2", "userOid2"));
        userList.add(new MidpointObject("user3", "userOid3"));

        when(midpointProvider.getUsers()).thenReturn(new GetMidpointObjectsResponse(userList));

        String result = midpointProvider.getUserOid(userName);

        assertEquals(null, result);
    }
}