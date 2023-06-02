package org.example.application;

import lombok.extern.log4j.Log4j2;
import org.example.application.fabric.Fabric;
import org.example.application.fabric.FabricImpl;
import org.example.application.fabric.JiraConfig;
import org.example.application.fabric.MidpointConfig;

import java.io.IOException;

@Log4j2
public class Main {


    public static void main(String[] args) throws IOException {
        try {
            MidpointConfig.loadConfigSecrets();
            JiraConfig.loadConfigSecrets();
        } catch (IOException e){
            log.error("Unable to load configs: " + e.getLocalizedMessage());
            throw e;
        }
        Fabric fabric = new FabricImpl();
        App app = new App(fabric.getJiraProvider(), fabric.getMidpointProvider());
        Runtime.getRuntime().addShutdownHook(new Thread(app::cancel));
        app.run();
    }
}