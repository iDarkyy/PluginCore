package me.idarkyy.plugincore.logging;

import org.bukkit.plugin.Plugin;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple logging to files & console for Bukkit plugins
 */
public class PluginLogger {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final SimpleDateFormat DEFAULT_FILENAME_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final String STRING_FORMAT = "%time% %level% [%plugin%] %message%";

    private Plugin plugin;
    private PrintStream output = System.out;
    private SimpleDateFormat dateFormat = DEFAULT_DATE_FORMAT;
    private File outputFile;

    public PluginLogger(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a file name for the current date
     *
     * @return file name with (.txt) extension
     */
    public static String createFileName() {
        return DEFAULT_FILENAME_DATE_FORMAT.format(Instant.now()).replace("/", "_") + ".txt";
    }

    public void log(Level level, String message) {
        output.println(build(message, level));
        writeToFile(message);
    }

    public void log(Level level, String message, Throwable throwable) {
        log(level, message);
        throwable.printStackTrace();
        writeToFile(throwable);
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    public void debug(String message, Throwable throwable) {
        log(Level.DEBUG, message, throwable);
    }

    public void warn(String message) {
        log(Level.WARN, message);
    }

    public void warn(String message, Throwable throwable) {
        log(Level.WARN, message, throwable);
    }

    public void severe(String message) {
        log(Level.SEVERE, message);
    }

    public void severe(String message, Throwable throwable) {
        log(Level.SEVERE, message, throwable);
    }

    public void error(String message) {
        log(Level.ERROR, message);
    }

    public void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }

    public void writeToFile(Throwable throwable) {
        List<String> lines = new ArrayList<>();

        lines.add("### This is a Java exception ###");
        lines.add("### Report this to the developer of %plugin% ###".replace("%plugin%", plugin.getName()));

        for (StackTraceElement element : throwable.getStackTrace()) {
            lines.add("## at " + element);
        }

        lines.add("### End of the Java exception ###");

        writeToFile(lines);
    }

    public void writeToFile(List<String> messages) {

        if (outputFile.exists()) {
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            for (String message : messages) {
                if (!message.startsWith(LINE_SEPARATOR) || message.endsWith(LINE_SEPARATOR)) {
                    message = LINE_SEPARATOR + message;
                }

                writer.write(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String message) {
        writeToFile(Collections.singletonList(message));
    }

    private String build(String message, Level level) {
        return STRING_FORMAT
                .replace("%time%", dateFormat.format(Instant.now()))
                .replace("%level%", level.toString())
                .replace("%plugin%", plugin.getName())
                .replace("%message%", message);
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Level of the log
     */
    public enum Level {
        INFO,
        DEBUG,
        FINE,
        LOW,
        NORMAL,
        HIGH,
        WARN,
        SEVERE,
        ERROR,
    }
}
