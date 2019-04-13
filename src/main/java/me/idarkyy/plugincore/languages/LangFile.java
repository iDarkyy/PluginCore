package me.idarkyy.plugincore.languages;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LangFile {
    private static String LINE_SEPARATOR = System.lineSeparator();
    private YamlConfiguration config;
    private File file;

    protected LangFile(File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public String get(String key) {
        if (config.isList(key)) {
            return join(config.getStringList(key), LINE_SEPARATOR);
        }

        return config.getString(key);
    }

    public void reload() throws InvalidConfigurationException, IOException {
        config.load(file);
    }

    public File getFile() {
        return file;
    }

    private String join(List<String> list, String delimeter) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.size());

            if (i != list.size() - 1) {
                sb.append(delimeter);
            }
        }

        return sb.toString();
    }
}
