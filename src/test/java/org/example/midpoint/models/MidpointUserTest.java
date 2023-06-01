import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MidpointUserTest {

    @Test
    public void testGetName() {
        MidpointUser user = new MidpointUser();
        String name = "John Doe";
        user.name = name;

        String result = user.getName();

        Assert.assertEquals(name, result);
    }

    @Test
    public void testGetOid() {
        MidpointUser user = new MidpointUser();
        String oid = "123456789";
        user.oid = oid;

        String result = user.getOid();

        Assert.assertEquals(oid, result);
    }

    @Test
    public void testGetAssignmentList() {
        MidpointUser user = new MidpointUser();
        List<Assignment> assignments = new ArrayList<>();
        assignments.add(new Assignment("role1"));
        assignments.add(new Assignment("role2"));
        user.assignments = assignments;

        List<Assignment> result = user.getAssignmentList();

        Assert.assertEquals(assignments, result);
    }
}
