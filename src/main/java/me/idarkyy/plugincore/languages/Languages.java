package me.idarkyy.plugincore.languages;

import me.idarkyy.plugincore.utils.Require;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Languages {
    private HashMap<String, LangFile> files = new HashMap<>();

    private Pattern pattern = Pattern.compile("language_(\\w{1,4})(.y(a)?ml)?");

    private Plugin plugin;

    public Languages(Plugin plugin) {
        this.plugin = plugin;
    }

    public void loadDirectory(File directory) {
        Require.nonNull(directory);
        Require.directory(directory);

        loadFiles(directory.listFiles());
    }

    public void loadFiles(File... files) {
        Arrays.stream(files).forEach(this::loadFile);
    }

    public void loadFile(File file) {
        Require.nonNull(file);
        Require.file(file);

        Matcher m = pattern.matcher(file.getName());

        if (m.find()) {
            loadFile(file, m.group(1));
        } else {
            throw new IllegalArgumentException("File name " + file.getName() + " does not match pattern: " + pattern.pattern());
        }
    }

    public void loadFile(File file, String codeName) {
        Require.nonNull(file);
        Require.file(file);

        files.put(codeName, new LangFile(file));
    }

    public LangFile getFile(String codeName) {
        return files.get(codeName);
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
