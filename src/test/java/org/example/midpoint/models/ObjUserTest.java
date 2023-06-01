import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ObjUsersTest {

    @Test
    public void testGetMidpointObjectsList() {
        ObjUsers objUsers = new ObjUsers();
        List<MidpointUser> users = new ArrayList<>();
        users.add(new MidpointUser("1", "John Doe"));
        users.add(new MidpointUser("2", "Jane Smith"));
        objUsers.midpointUserList = users;

        List<MidpointUser> result = objUsers.getMidpointObjectsList();

        Assert.assertEquals(users, result);
    }
}