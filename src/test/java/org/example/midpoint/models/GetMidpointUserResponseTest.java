import org.junit.Assert;
import org.junit.Test;

public class GetMidpointUserResponseTest {

    @Test
    public void testGetObj() {
        GetMidpointUserResponse response = new GetMidpointUserResponse();

        ObjUsers obj = new ObjUsers();

        response.obj = obj;

        ObjUsers result = response.getObj();

        Assert.assertEquals(obj, result);
    }
}