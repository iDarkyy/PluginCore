package me.idarkyy.plugincore.commands.event;

import me.idarkyy.plugincore.commands.CommandManager;
import org.bukkit.command.CommandSender;

public class CommandEvent {
    private CommandManager commandManager;
    private CommandSender sender;
    private String[] arguments;

    public CommandEvent(CommandManager commandManager, CommandSender sender, String[] arguments) {
        this.commandManager = commandManager;
        this.sender = sender;
        this.arguments = arguments;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String[] getArguments() {
        return arguments;
    }
}
