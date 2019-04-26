package me.idarkyy.plugincore.worldmanager.options.all;

import me.idarkyy.plugincore.worldmanager.options.WorldOption;

public class GenerateStructures extends WorldOption<Boolean> {
    public GenerateStructures(boolean generate) {
        index = "genstructures";

        setValue(generate);
    }

    public static GenerateStructures option(boolean generate) {
        return new GenerateStructures(generate);
    }
}
