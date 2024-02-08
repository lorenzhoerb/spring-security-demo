package com.lorenzhoerb.securitydemo.util;

public class TextUtils {

    public static String toTitleCase(String title) {
        if (title == null || title.isEmpty()) {
            return title;
        }

        // Split the input string into words
        String[] words = title.split("\\s+");

        StringBuilder result = new StringBuilder();

        for (String word : words) {
            // Convert the first character of each word to uppercase
            char firstChar = Character.toUpperCase(word.charAt(0));

            result.append(firstChar).append(word.substring(1)).append(" ");
        }

        return result.toString().trim();
    }
}
