package org.example.midpoint;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static String MIDPOINT_BASE_URL = null;
    public static String ENABLE_USER_BODY = null;
    public static String DISABLE_USER_BODY = null;
    public static String MIDPOINT_LOGIN = null;
    public static String MIDPOINT_PASSWORD = null;

    public static void loadConfig() {
        Properties properties = new Properties();
        try {
            InputStream ins = Config.class.getResourceAsStream("/midpoint_config.properties");
            properties.load(ins);
            MIDPOINT_BASE_URL = properties.getProperty("midpoint_base_url");
            ENABLE_USER_BODY = properties.getProperty("enable_user_body");
            DISABLE_USER_BODY = properties.getProperty("disable_user_body");
            MIDPOINT_LOGIN = properties.getProperty("midpoint_login");
            MIDPOINT_PASSWORD = properties.getProperty("midpoint_password");
        }
        catch (IOException e) {

        }
    }
}
