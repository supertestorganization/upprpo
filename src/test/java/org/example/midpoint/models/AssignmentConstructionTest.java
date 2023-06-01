package org.example.midpoint.models;

import org.junit.Assert;
import org.junit.Test;

public class AssignmentConstructionTest {

    @Test
    public void testGetResourceRef() {
        AssignmentConstruction construction = new AssignmentConstruction();
        ResourceRef resourceRef = new ResourceRef();
        construction.resourceRef = resourceRef;

        Assert.assertEquals(resourceRef, construction.getResourceRef());
    }

    @Test
    public void testGetResourceRef_Null() {
        AssignmentConstruction construction = new AssignmentConstruction();

        Assert.assertNull(construction.getResourceRef());
    }
}