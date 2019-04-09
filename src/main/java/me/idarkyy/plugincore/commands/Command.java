package me.idarkyy.plugincore.commands;

import me.idarkyy.plugincore.commands.event.CommandEvent;
import me.idarkyy.plugincore.commands.event.MissingPermissionEvent;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface Command {
    /**
     * The method is invoked if the Command#check() method passes with a positive boolean
     * @param event The command event, contains the command sender, arguments and the command manager
     */
    void execute(CommandEvent event);

    /**
     * Allows checking should the main event trigger (Command#execute)
     * Triggers before the command event
     * @param event The command event
     * @return Should the event trigger, true = continue
     */
    boolean check(CommandEvent event);

    /**
     * Triggers if permissions from the annotation @Permission aren't allowed for the player
     * @param event the event
     */
    void onMissingPermission(MissingPermissionEvent event);

    /**
     * Triggers if permissions from the annotation @Permission aren't allowed for the player
     * This executes if the player doesn't have permission for the specified subcommand
     * @param event the event
     */
    void onSubcommandMissingPermission(MissingPermissionEvent event);

    /**
     * Allows the modification of the tab completer
     * @param sender The command sender
     * @param alias The command alias
     * @param args All of the arguments
     * @return Should return all of the tab completers
     */
    List<String> tabComplete(CommandSender sender, String alias, String[] args);
}
