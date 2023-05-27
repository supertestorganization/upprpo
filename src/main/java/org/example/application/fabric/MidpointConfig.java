package org.example.application.fabric;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MidpointConfig {
    public static String MIDPOINT_BASE_URL = null;
    public static String ENABLE_USER_BODY = null;
    public static String DISABLE_USER_BODY = null;
    public static String ENABLE_ACCOUNT_BODY = null;
    public static String DISABLE_ACCOUNT_BODY = null;
    public static String MIDPOINT_LOGIN = null;
    public static String MIDPOINT_PASSWORD = null;

    private MidpointConfig() {

    }

    public static void loadConfig() throws IOException {
        Properties properties = new Properties();
        InputStream ins = MidpointConfig.class.getResourceAsStream("/midpoint_config.properties");
        properties.load(ins);
        MIDPOINT_BASE_URL = properties.getProperty("midpoint_base_url");
        ENABLE_USER_BODY = properties.getProperty("enable_user_body");
        DISABLE_USER_BODY = properties.getProperty("disable_user_body");
        ENABLE_ACCOUNT_BODY = properties.getProperty("enable_account_body");
        DISABLE_ACCOUNT_BODY = properties.getProperty("disable_account_body");
        MIDPOINT_LOGIN = properties.getProperty("midpoint_login");
        MIDPOINT_PASSWORD = properties.getProperty("midpoint_password");

    }
}
