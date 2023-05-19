package org.example.application.fabric;

import org.example.jira.JiraProvider;
import org.example.midpoint.MidpointProvider;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class FabricAndConfigTest {

    @Before
    public void load_configs() throws IOException{
        JiraConfig.loadConfig();
        MidpointConfig.loadConfig();
    }

    @Test
    public void fabric_test() throws IOException {

        Fabric fabric = new FabricImpl();

        JiraProvider jiraProvider = fabric.getJiraProvider();
        MidpointProvider midpointProvider= fabric.getMidpointProvider();

        assertNotNull(jiraProvider);
        assertNotNull(midpointProvider);

    }

}