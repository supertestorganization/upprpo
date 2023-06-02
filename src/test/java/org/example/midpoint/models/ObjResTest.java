package org.example.midpoint.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ObjResTest {

    @Test
    public void testGetMidpointResources() {
        ObjRes objRes = new ObjRes();
        List<MidpointResource> resources = new ArrayList<>();
        resources.add(new MidpointResource());
        resources.add(new MidpointResource());
        objRes.resourceObjs = resources;

        List<MidpointResource> result = objRes.getMidpointResources();

        Assert.assertEquals(resources, result);
    }
}
