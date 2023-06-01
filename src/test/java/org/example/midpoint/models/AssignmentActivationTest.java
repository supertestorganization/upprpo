package org.example.midpoint.models;

import org.junit.Assert;
import org.junit.Test;

public class AssignmentActivationTest {

    @Test
    public void testGetEffectiveStatus() {
        AssignmentActivation activation = new AssignmentActivation();
        activation.effectiveStatus = "disabled";
        Assert.assertEquals("disabled", activation.effectiveStatus);
    }
}
