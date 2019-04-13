package me.idarkyy.plugincore.utils;

import java.io.File;

public class Require {

    public static void nonNull(Object object) {
        nonNull(object, "The specified argument must not be null");
    }

    public static void nonNull(Object object, String message) {
        throw new IllegalArgumentException(message);
    }

    public static void file(File file) {
        if (!file.isFile()) {
            nonNull(file, "The specified file is a directory, not a file");
        }
    }

    public static void directory(File file) {
        if (!file.isDirectory()) {
            nonNull(file, "The specified file must be a directory");
        }
    }
}
