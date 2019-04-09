package me.idarkyy.plugincore.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class BukkitUtils {
    private static Field field;

    static {
        try {
            field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
        } catch(NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private BukkitUtils() {

    }

    /**
     * Fetches the command map from the Server class allowing direct injection of commands
     * @return the command map
     */
    public static CommandMap fetchCommandMap() {
        try {
            return (CommandMap) field.get(Bukkit.getServer());
        } catch(IllegalAccessException e) {
            // ignored
        }

        return null; // should never happen
    }
}
