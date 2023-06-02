package org.example.application.fabric;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Log4j2
public class JiraConfig {

    public static String LOGIN = null;
    public static String API_TOKEN = null;
    public static String BASE_URL = null;
    public static String SEARCH_JQL = null;
    public static String TRANSITION_TEMPLATE = null;
    public static String DESCRIPTION_TEMPLATE = null;


    public static void loadConfig() throws IOException{
        Properties properties = new Properties();
        InputStream ins = MidpointConfig.class.getResourceAsStream("/jira_config.properties");
        properties.load(ins);
        BASE_URL = properties.getProperty("base_url");
        LOGIN = properties.getProperty("login");
        API_TOKEN = properties.getProperty("api_token");
        SEARCH_JQL = properties.getProperty("search_jql");
        TRANSITION_TEMPLATE = properties.getProperty("transition_template");
        DESCRIPTION_TEMPLATE = properties.getProperty("description_template");
        if (null == LOGIN || LOGIN.isEmpty()){
            log.warn("Jira login is not provided.");
        }
        if (null == API_TOKEN || API_TOKEN.isEmpty()){
            log.warn("Jira api token is not provided.");
        }
    }

    public static void loadConfigSecrets() throws IOException {
        Properties properties = new Properties();
        InputStream ins = JiraConfig.class.getResourceAsStream("/jira_config.properties");
        properties.load(ins);
        BASE_URL = System.getenv("JNM_JIRA_API_URL");
        LOGIN = System.getenv("JNM_JIRA_LOGIN");
        API_TOKEN = Files.readString(Path.of(System.getenv("JNM_JIRA_TOKEN_FILE"))).replace("\n", "");
        SEARCH_JQL = properties.getProperty("search_jql");
        TRANSITION_TEMPLATE = properties.getProperty("transition_template");
        DESCRIPTION_TEMPLATE = properties.getProperty("description_template");
        if (null == LOGIN || LOGIN.isEmpty()){
            log.warn("Jira login is not provided.");
        }
        if (null == API_TOKEN || API_TOKEN.isEmpty()){
            log.warn("Jira api token is not provided.");
        }
    }
}
