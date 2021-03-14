package com.wizatar08.escapemaze.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class Lang {
    public static Locale CURRENT_LANG;

    public static void start() {
        Properties prop = new Properties();
        try {
            InputStream file = new FileInputStream("src/resources/settings/settings.properties");
            prop.load(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String lang = prop.getProperty("lang");
        CURRENT_LANG = new Locale(lang.substring(0, 2), lang.substring(3, 5));
    }

    public static String get(String translationKey) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/lang.bundle", CURRENT_LANG);
        try {
            return (resourceBundle.getString(translationKey));
        } catch (MissingResourceException e) {
            return translationKey;
        }
    }

    public static void changeLang(Locale lang) {
        CURRENT_LANG = lang;
    }
}
