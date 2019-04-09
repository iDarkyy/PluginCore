package me.idarkyy.plugincore.commands.event;

import me.idarkyy.plugincore.commands.CommandManager;
import org.bukkit.command.CommandSender;

public class MissingPermissionEvent extends CommandEvent {
    private String[] permissions;

    public MissingPermissionEvent(CommandManager commandManager, CommandSender sender, String[] arguments, String[] permissions) {
        super(commandManager, sender, arguments);
        this.permissions = permissions;
    }

    public String[] getPermissions() {
        return permissions;
    }
}
