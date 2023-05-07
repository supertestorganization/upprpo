package org.example.midpoint;

import org.example.midpoint.exceptions.BadResource;
import org.example.midpoint.exceptions.BadUser;

import java.io.IOException;

public interface MidpointProvider {
    void disableAccount(String userName, String resourceName) throws BadResource, BadUser, IOException;
    void enableAccount(String userName, String resourceName) throws BadResource, BadUser, IOException;
    void disableUser(String userName) throws BadUser, IOException;
    void enableUser(String userName) throws BadUser, IOException;

}
