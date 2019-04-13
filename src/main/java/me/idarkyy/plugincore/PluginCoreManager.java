package me.idarkyy.plugincore;

import me.idarkyy.plugincore.listener.GlobalListener;
import me.idarkyy.plugincore.plugin.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PluginCoreManager extends BukkitPlugin {
    private static boolean initialized;

    public static void initialize(Plugin plugin) {
        if(initialized) {
            return;
        }

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new GlobalListener(), plugin);
    }

    @Override
    public void onEnable() {
        initialize(this);
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
