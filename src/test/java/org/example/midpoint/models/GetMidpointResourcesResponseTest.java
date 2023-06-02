package org.example.midpoint.models;

import org.junit.Assert;
import org.junit.Test;

public class GetMidpointResourcesResponseTest {

    @Test
    public void testGetObjRes() {
        GetMidpointResourcesResponse response = new GetMidpointResourcesResponse();

        ObjRes objRes = new ObjRes();

        response.objRes = objRes;

        ObjRes result = response.getObjRes();

        Assert.assertEquals(objRes, result);
    }
}
