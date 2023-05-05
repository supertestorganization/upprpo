package org.example.midpoint;

import org.example.midpoint.exceptions.BadResource;
import org.example.midpoint.exceptions.BadUser;

import java.io.IOException;

public interface MidpointProvider {
    void disableUser(String userName, String resourceName) throws BadResource, BadUser, IOException;
    void activateUser(String userName, String resourceName) throws BadResource, BadUser, IOException;
}
