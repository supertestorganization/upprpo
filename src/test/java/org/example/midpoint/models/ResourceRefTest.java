import org.junit.Assert;
import org.junit.Test;

public class ResourceRefTest {

    @Test
    public void testGetOid() {
        ResourceRef resourceRef = new ResourceRef();
        String oid = "123456789";
        resourceRef.oid = oid;

        String result = resourceRef.getOid();

        Assert.assertEquals(oid, result);
    }
}
