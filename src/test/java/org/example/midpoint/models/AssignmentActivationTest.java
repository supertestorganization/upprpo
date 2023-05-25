import org.junit.Assert;
import org.junit.Test;

public class AssignmentActivationTest {

    @Test
    public void testGetEffectiveStatus() {
        AssignmentActivation activation = new AssignmentActivation();
        activation.effectiveStatus = "disabled";

        Assert.assertEquals("disabled", activation.getEffectiveStatus());
    }

    @Test
    public void testGetEffectiveStatus() {
        AssignmentActivation activation = new AssignmentActivation();
        activation.effectiveStatus = "active";

        Assert.assertEquals("active", activation.getEffectiveStatus());
    }

    @Test
    public void testGetEffectiveStatus_Null() {
        AssignmentActivation activation = new AssignmentActivation();

        Assert.assertNull(activation.getEffectiveStatus());
    }
}
