import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ObjResTest {

    @Test
    public void testGetMidpointResources() {
        ObjRes objRes = new ObjRes();
        List<MidpointResource> resources = new ArrayList<>();
        resources.add(new MidpointResource("1", "Resource 1"));
        resources.add(new MidpointResource("2", "Resource 2"));
        objRes.resourceObjs = resources;

        List<MidpointResource> result = objRes.getMidpointResources();

        Assert.assertEquals(resources, result);
    }
}
