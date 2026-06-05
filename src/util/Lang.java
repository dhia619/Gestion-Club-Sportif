package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Lang {
    private static ResourceBundle bundle = ResourceBundle.getBundle("lang.messages", new Locale("ar"));

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("lang.messages", locale);
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static Locale getLocale() {
        return bundle.getLocale();
    }
}