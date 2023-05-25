import org.junit.Assert;
import org.junit.Test;

public class AssignmentTest {

    @Test
    public void testGetId() {
        Assignment assignment = new Assignment();
        assignment.id = 1;

        Assert.assertEquals(Integer.valueOf(1), assignment.getId());
    }

    @Test
    public void testGetActivation() {
        Assignment assignment = new Assignment();
        AssignmentActivation activation = new AssignmentActivation();
        assignment.activation = activation;

        Assert.assertEquals(activation, assignment.getActivation());
    }

    @Test
    public void testGetConstruction() {
        Assignment assignment = new Assignment();
        AssignmentConstruction construction = new AssignmentConstruction();
        assignment.construction = construction;

        Assert.assertEquals(construction, assignment.getConstruction());
    }
}
