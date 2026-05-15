package util;

import java.util.Locale;
import java.util.Properties;

public class LanguageHandler {

    private static Properties config;
    private static String locale = "fr";

    public static void init() {
        config = ConfigurationFileHandler.getConfig();
        locale = config.getProperty("locale", "fr");
        setLocale(locale);
    }

    public static void setLocale(String language) {
        locale = language;
        Lang.setLocale(new Locale(language));

        if (locale.equals("ar")) {
            UIConstants.titleFont = "Dubai";
            UIConstants.tableFont = "Dubai";
        } else {
            UIConstants.titleFont = "Georgia";
            UIConstants.tableFont = "Bahnschrift";
        }
    }

    public static void saveLanguagePreference(String language) {
        setLocale(language);
        config.setProperty("locale", language);
        ConfigurationFileHandler.saveConfig(config);
    }

    public static String getLocale() {
        return locale;
    }

    public static boolean isArabic() {
        return locale.equals("ar");
    }

    public static String getTitleFont() {
        return isArabic() ? "Dubai" : "Georgia";
    }
}