package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ConfigurationFileHandler {

    public static Properties getConfig() {
        Properties config = new Properties();

        try {
            config.load(new FileInputStream("config.properties"));
        } catch (Exception e) {
            config.setProperty("locale", "fr");
            config.setProperty("remember_me", "false");
            saveConfig(config);
        }

        return config;
    }

    public static void saveConfig(Properties config) {
        try {
            config.store(new FileOutputStream("config.properties"), null);
        }
        catch (Exception e) {
            System.out.println("Error while storing config file :"+e.getMessage());
        }
    }
}
