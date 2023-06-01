import org.junit.Assert;
import org.junit.Test;

public class MidpointResourceTest {

    @Test
    public void testGetResourceOid() {
        MidpointResource resource = new MidpointResource();
        String oid = "123456789";
        resource.resourceOid = oid;

        String result = resource.getResourceOid();

        Assert.assertEquals(oid, result);
    }

    @Test
    public void testGetResourceName() {
        MidpointResource resource = new MidpointResource();
        String name = "Test Resource";
        resource.resourceName = name;

        String result = resource.getResourceName();

        Assert.assertEquals(name, result);
    }
}
