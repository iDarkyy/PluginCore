package me.idarkyy.plugincore.utils;

public class NumberUtils {
    private NumberUtils() {

    }

    public static boolean isLong(String string) {
        try {
            Long.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            // ignored
        }

        return false;
    }
}
