package org.example.application.fabric;

import org.example.jira.JiraProvider;
import org.example.midpoint.MidpointProvider;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FabricAndConfigTest {

    private Fabric fabric;
    @Before
    public void load_configs_create_fabric() throws IOException{
        JiraConfig.loadConfig();
        MidpointConfig.loadConfig();
        fabric = new FabricImpl();
    }

    @Test
    public void fabric_jira_provider(){
        assertNotNull(fabric);

        JiraProvider jiraProvider = fabric.getJiraProvider();
        assertNotNull(jiraProvider);

    }

    @Test
    public void fabric_midpoint_provider() {
        assertNotNull(fabric);

        MidpointProvider midpointProvider= fabric.getMidpointProvider();
        assertNotNull(midpointProvider);

    }

}