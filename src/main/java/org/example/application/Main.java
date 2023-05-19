package org.example.application;

import org.example.application.fabric.Fabric;
import org.example.application.fabric.FabricImpl;
import org.example.application.fabric.JiraConfig;
import org.example.application.fabric.MidpointConfig;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        MidpointConfig.loadConfig();
        JiraConfig.loadConfig();
        Fabric fabric = new FabricImpl();
        App app = new App(fabric.getJiraProvider(), fabric.getMidpointProvider());
        Runtime.getRuntime().addShutdownHook(new Thread(app::cancel));
        app.run();
    }
}