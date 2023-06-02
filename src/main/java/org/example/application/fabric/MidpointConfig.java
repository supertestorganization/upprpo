package org.example.application.fabric;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static void loadConfigSecrets() throws IOException {
        Properties properties = new Properties();
        InputStream ins = MidpointConfig.class.getResourceAsStream("/midpoint_config.properties");
        properties.load(ins);
        MIDPOINT_BASE_URL = System.getenv("JNM_MIDPOINT_API_URL");
        var url_fom_props = properties.getProperty("midpoint_base_url");
        System.out.println("URL_FROM_PROPS: " + url_fom_props);
        System.out.println("URL_FROM_FILE: " + MIDPOINT_BASE_URL);
        System.out.println("EQUALITY: " + MIDPOINT_BASE_URL.equals(url_fom_props));
        ENABLE_USER_BODY = properties.getProperty("enable_user_body");
        DISABLE_USER_BODY = properties.getProperty("disable_user_body");
        ENABLE_ACCOUNT_BODY = properties.getProperty("enable_account_body");
        DISABLE_ACCOUNT_BODY = properties.getProperty("disable_account_body");
        MIDPOINT_LOGIN = System.getenv("JNM_MIDPOINT_LOGIN");
        var login_from_props = properties.getProperty("midpoint_login");
        System.out.println("LOGIN_FROM_PROPS: " + login_from_props);
        System.out.println("LOGIN_FROM_FILE: " + MIDPOINT_LOGIN);
        System.out.println("EQUALITY: " + MIDPOINT_LOGIN.equals("administrator"));
        MIDPOINT_PASSWORD = Files.readString(Path.of(System.getenv("JNM_MIDPOINT_PASSWORD_FILE"))).replace("\n", "");
        System.out.println("PASS_FROM_PROPS: " + properties.getProperty("midpoint_password"));
        System.out.println("PASS_FROM_FILE: " + MIDPOINT_PASSWORD);
        System.out.println("EQUALITY: " + MIDPOINT_PASSWORD.equals(properties.getProperty("midpoint_password")));
    }

}
