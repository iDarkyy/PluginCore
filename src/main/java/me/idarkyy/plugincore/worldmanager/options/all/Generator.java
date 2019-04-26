package me.idarkyy.plugincore.worldmanager.options.all;

import me.idarkyy.plugincore.worldmanager.options.WorldOption;

public class Generator extends WorldOption<String> {
    private Generator(String generator) {
        index = "gen";
        setValue(generator);
    }

    public static Generator of(String generator) {
        return new Generator(generator);
    }
}
