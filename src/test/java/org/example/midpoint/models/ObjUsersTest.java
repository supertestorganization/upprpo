package org.example.midpoint.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ObjUsersTest {

    @Test
    public void testGetMidpointObjectsList() {
        ObjUsers objUsers = new ObjUsers();
        List<MidpointUser> users = new ArrayList<>();
        users.add(new MidpointUser());
        users.add(new MidpointUser());
        objUsers.midpointUserList = users;

        List<MidpointUser> result = objUsers.getMidpointObjectsList();

        Assert.assertEquals(users, result);
    }
}