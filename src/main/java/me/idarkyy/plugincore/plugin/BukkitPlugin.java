package me.idarkyy.plugincore.plugin;

import me.idarkyy.plugincore.commands.CommandManager;
import me.idarkyy.plugincore.utils.BukkitUtils;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin extends JavaPlugin {
    private static CommandMap commandMap = BukkitUtils.fetchCommandMap(); // global command map

    private CommandManager commandManager; // the plugin's command manager

    public BukkitPlugin() {
        this.commandManager = new CommandManager(this);
    }

    /**
     * Returns the command map
     * Commands are injectable
     * @return The command map
     */
    public CommandMap getCommandMap() {
        return commandMap;
    }

    /**
     * Returns this plugin's command manager
     * Used to register/manage commands
     * @return The command manager
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }
}
