package ru.bragakap.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {
    private static final Properties properties = new Properties();

    public static void initSettings(String path) {
        if(path == null) {
            initDefaultProperty();
            return;
        }

        try {
            InputStream input;
            input = new FileInputStream(path);
            properties.load(input);
        } catch (IOException e) {
            initDefaultProperty();
        }
    }

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }

    private static void initDefaultProperty() {

        properties.setProperty("food.width", "5");
        properties.setProperty("food.height", "5");

        properties.setProperty("ghost.width", "30");
        properties.setProperty("ghost.height", "30");

        properties.setProperty("pacman.width", "15");
        properties.setProperty("pacman.height", "15");

        properties.setProperty("imp.width", "30");
        properties.setProperty("imp.height", "30");

        properties.setProperty("feeder.width", "30");
        properties.setProperty("feeder.height", "30");

        properties.setProperty("wall.width", "30");
        properties.setProperty("wall.height", "30");

        properties.setProperty("fireball.width", "5");
        properties.setProperty("fireball.height", "5");

        properties.setProperty("aegis.width", "10");
        properties.setProperty("aegis.height", "10");
    }
}
