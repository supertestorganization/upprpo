package org.example.midpoint;

import java.io.IOException;

public interface MidpointProvider {
    OperationResult disableAccount(String userName, String resourceName) throws IOException;
    OperationResult activateAccount(String userName, String resourceName) throws IOException;
    OperationResult disableUser(String userName) throws IOException;
    OperationResult activateUser(String userName) throws IOException;

}
