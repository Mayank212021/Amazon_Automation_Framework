package Utilities;

import java.io.InputStream;
import java.util.Properties;

public class TestCredentials {

    private static final Properties localCredentials = new Properties();

    static {
        try (InputStream input = TestCredentials.class
                .getClassLoader()
                .getResourceAsStream("config/local-credentials.properties")) {

            if (input != null) {
                localCredentials.load(input);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "❌ Unable to load local credentials file", e);
        }
    }

    public static String getUsername(String user) {

        // Jenkins
        String jenkinsUsername = System.getenv("AMAZON_CREDENTIALS_USR");

        if (jenkinsUsername != null && !jenkinsUsername.isBlank()) {
            return jenkinsUsername;
        }

        // Eclipse
        String localUsername = localCredentials.getProperty(user + ".username");

        if (localUsername != null && !localUsername.isBlank()) {
            return localUsername;
        }

        throw new RuntimeException(
                "❌ Username not found for user: " + user);
    }

    public static String getPassword(String user) {

        // Jenkins
        String jenkinsPassword = System.getenv("AMAZON_CREDENTIALS_PSW");

        if (jenkinsPassword != null && !jenkinsPassword.isBlank()) {
            return jenkinsPassword;
        }

        // Eclipse
        String localPassword = localCredentials.getProperty(user + ".password");

        if (localPassword != null && !localPassword.isBlank()) {
            return localPassword;
        }

        throw new RuntimeException(
                "❌ Password not found for user: " + user);
    }
}