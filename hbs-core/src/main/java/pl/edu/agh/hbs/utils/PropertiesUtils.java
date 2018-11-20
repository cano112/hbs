package pl.edu.agh.hbs.utils;

import java.util.Properties;

public class PropertiesUtils {

    public static int getIntegerValue(final Properties properties, final String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static boolean getBooleanValue(final Properties properties, final String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }
}
