import org.example.midpoint.OperationResult;
import org.example.midpoint.OperationResult.OPERATION_STATUS;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OperationResultTest {

    @Test
    public void testOperationStatusFailed() {
        OPERATION_STATUS expectedStatus = OPERATION_STATUS.FAILED;
        String expectedMessage = "Operation failed";

        OperationResult result = new OperationResult(expectedStatus, expectedMessage);

        assertEquals(expectedStatus, result.status());
        assertEquals(expectedMessage, result.msg());
    }

    @Test
    public void testOperationStatusSucceed() {
        OPERATION_STATUS expectedStatus = OPERATION_STATUS.SUCCEED;
        String expectedMessage = "Operation succeeded";

        OperationResult result = new OperationResult(expectedStatus, expectedMessage);

        assertEquals(expectedStatus, result.status());
        assertEquals(expectedMessage, result.msg());
    }
}