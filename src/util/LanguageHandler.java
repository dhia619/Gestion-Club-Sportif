package util;

import java.nio.file.*;
import java.util.Locale;

public class LanguageHandler {

    private static Path path = Paths.get("./language.txt");

    private static String locale = "fr";

    public static void init() {

        try {

            if (Files.exists(path)) {

                locale = Files.readString(path).trim();

                setLocale(locale);
            }

        } catch (Exception e) {

            System.out.println("Error while reading config file: " + e.getMessage());
        }
    }

    public static void setLocale(String language) {
        locale = language;
        Lang.setLocale(new Locale(language));
        if (locale.equals("ar")) {
            UIConstants.titleFont = "Dubai";
            UIConstants.tableFont = "Dubai";
        }
        else {
            UIConstants.titleFont = "Georgia";
            UIConstants.tableFont = "Bahnschrift";
        }
    }

    public static void saveLanguagePreference(String language) {

        try {

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            Files.writeString(path, language);

        } catch (Exception e){

            System.out.println("Error when saving file: " + e.getMessage());
        }
    }

    public static String getLocale() {
        return locale;
    }
}