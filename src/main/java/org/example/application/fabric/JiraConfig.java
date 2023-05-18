package org.example.application.fabric;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JiraConfig {

    public static String LOGIN = null;
    public static String API_TOKEN = null;
    public static String BASE_URL = null;


    public static void loadConfig() throws IOException{
        Properties properties = new Properties();
        InputStream ins = MidpointConfig.class.getResourceAsStream("/jira_config.properties");
        properties.load(ins);
        BASE_URL = properties.getProperty("base_url");
        LOGIN = properties.getProperty("login");
        API_TOKEN = properties.getProperty("api_token");
    }
}
