package me.idarkyy.plugincore.worldmanager.options.all;

import me.idarkyy.plugincore.worldmanager.options.WorldOption;
import org.bukkit.World;

public class Environment extends WorldOption<World.Environment> {
    public Environment(World.Environment environment) {
        index = "env";
        setValue(environment);
    }

    public static Environment of(World.Environment environment) {
        return new Environment(environment);
    }
}
