package org.example.midpoint;

import org.example.midpoint.exceptions.BadResource;
import org.example.midpoint.exceptions.BadUser;

import java.io.IOException;

public interface MidpointProvider {
    OperationResult disableAccount(String userName, String resourceName) throws IOException;
    OperationResult activateAccount(String userName, String resourceName) throws IOException;
    OperationResult disableUser(String userName) throws IOException;
    OperationResult activateUser(String userName) throws IOException;

}
