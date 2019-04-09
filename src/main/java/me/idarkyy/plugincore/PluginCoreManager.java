package me.idarkyy.plugincore;

import me.idarkyy.plugincore.listener.GlobalListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PluginCoreManager {
    private static boolean initialized;

    public static void initialize(Plugin plugin) {
        if(initialized) {
            return;
        }

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new GlobalListener(), plugin);
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
