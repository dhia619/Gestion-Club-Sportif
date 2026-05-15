package util;

import java.security.MessageDigest;

public class HashUtil {

    public static String hash(String text) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = md.digest(text.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            System.err.println("Error hashing text: " + e.getMessage());
            return null;
        }
    }
}