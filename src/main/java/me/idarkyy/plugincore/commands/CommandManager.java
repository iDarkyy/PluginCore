package me.idarkyy.plugincore.commands;

import me.idarkyy.plugincore.commands.annotations.Permission;
import me.idarkyy.plugincore.commands.annotations.Subcommand;
import me.idarkyy.plugincore.commands.event.CommandEvent;
import me.idarkyy.plugincore.commands.event.MissingPermissionEvent;
import me.idarkyy.plugincore.commands.event.SubcommandEvent;
import me.idarkyy.plugincore.utils.BukkitUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.*;

public class CommandManager {
    private static CommandMap commandMap = BukkitUtils.fetchCommandMap();

    private HashMap<org.bukkit.command.Command, HashMap<String, Method>> subcommands = new HashMap<>();

    private HashMap<org.bukkit.command.Command, List<String>> permissions = new HashMap<>();
    private HashMap<org.bukkit.command.Command, HashMap<String, List<String>>> subcommandPermissions = new HashMap<>();

    private JavaPlugin plugin;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers a command
     *
     * Registers a command into the bukkit command map and
     * allows it's registration registering them in without plugin.yml
     *
     * @param name The command name
     * @param command The command object
     * @param aliases Aliases for the command (optional)
     */
    public void register(String name, Command command, String... aliases) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(command);

        BukkitCommand commandObject = new BukkitCommand(name) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                CommandEvent mainEvent = new CommandEvent(CommandManager.this, sender, args);

                if(!command.check(mainEvent)) {
                    return false;
                }

                // If the sender does not have at least one permission
                // the #onMissingPermission method will be triggered
                if (!hasAtleastOnePermission(sender, permissions.getOrDefault(this, Collections.emptyList()))) {
                    command.onMissingPermission(
                            new MissingPermissionEvent(CommandManager.this, sender, args, permissions.get(this).toArray(new String[permissions.get(this).size() - 1]))
                    );

                    return false;
                }

                // If the argument length is above zero
                // the arguments will be possible subcommands
                if(args.length > 0) {
                    Method method = CommandManager.this.subcommands.get(this).get(args[0]);

                    // Checks if the subcommand exists
                    if(method != null) {
                        // If the sender does not have at least one permission to use the subcommand
                        // the #onSubcommandMissingPermission method will be triggered
                        if(!hasAtleastOnePermission(sender, subcommandPermissions.get(this).getOrDefault(args[0], Collections.emptyList()))) {
                            command.onSubcommandMissingPermission(
                                    new MissingPermissionEvent(CommandManager.this, sender, args, permissions.get(this).toArray(new String[permissions.get(this).size() - 1]))
                            );

                            return false;
                        }

                        // Creates a new event object, which will be used to get the sender and arguments
                        SubcommandEvent event = new SubcommandEvent(CommandManager.this, sender, Arrays.copyOfRange(args, 1, args.length - 1))      ;

                        // Attempt to invoke the method
                        try {
                            method.invoke(command, event);
                            return true;
                        } catch(Exception e) {
                            // Throws a CommandException with the thrown exception as a cause
                            throw new CommandException(
                                    "An error occurred whilst executing command " + name + "'s subcommand '" + args[0] + "' from plugin " + plugin.getName() + " " + plugin.getDescription().getVersion()
                                    , e);
                        }
                    }
                }

                // Attempts to execute the command
                try {
                    command.execute(mainEvent);
                } catch(Exception e) {
                    // If the command threw an exception, it'll be re-thrown here
                    throw new CommandException("An error occurred whilst executing command '" + name + "' from plugin " + plugin.getName() + " " + plugin.getDescription().getVersion(), e);
                }

                return true;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
                return command.tabComplete(sender, alias, args);
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
                return command.tabComplete(sender, alias, args);
            }
        };

        HashMap<String, Method> subcommands = new HashMap<>(); // a map of the subcommand name and the method
        HashMap<String, List<String>> subcommandPermissions = new HashMap<>(); // a map of the subcommand name and all of the permissions

        try {
            if (command.getClass().isAnnotationPresent(Permission.class)) {
                permissions.put(commandObject, Arrays.asList(command.getClass().getAnnotation(Permission.class).value()));
            }
        } catch(Exception e) {
            throw new RuntimeException("Could not register permissions for the main command", e);
        }

        for(Method method : command.getClass().getMethods()) {
            if(method.isAnnotationPresent(Subcommand.class) // Checks if the method is annotated with @Subcommand
                    && (method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == SubcommandEvent.class)) { // Checks if the first paremeter is SubcommandEvent

                //...//
                String scName = method.getAnnotation(Subcommand.class).value(); // gets the subcommand name from the annotation

                if(method.isAnnotationPresent(Permission.class)) { // Checks if the method is annotated with @Permission
                    List<String> perms = Arrays.asList(method.getAnnotation(Permission.class).value()); // gets the permissions

                    subcommandPermissions.put(scName, perms); // sets the permissions for this subcommand
                }

                subcommands.put(scName, method); // sets the subcommand method for this subcommand
            }
        }

        this.subcommands.put(commandObject, subcommands); // finally sets the subcommands for this command objects
        this.subcommandPermissions.put(commandObject, subcommandPermissions); // finally sets the permissions for this command object

        commandObject.setName(name); // sets the command name to the field 'name', modifiable
        commandObject.setAliases(Arrays.asList(aliases)); // sets the command aliases, also modifiable

        commandMap.register(plugin.getName().toLowerCase(), commandObject); // Finally registers the command
    }

    private boolean hasAtleastOnePermission(CommandSender sender, List<String> permissions) {
        if(permissions.isEmpty()) {
            return true; // if there's no permissions, returns true
        }

        for (String permission : permissions) { // loops through the permissions
            if (sender.hasPermission(permission)) {
                return true; // if the sender has at least one permission, returns true
            }
        }

        return false;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
