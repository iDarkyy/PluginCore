package me.idarkyy.plugincore.worldmanager;

import me.idarkyy.plugincore.worldmanager.builder.WorldBuilder;
import me.idarkyy.plugincore.worldmanager.options.WorldOption;
import me.idarkyy.plugincore.worldmanager.world.ServerWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.HashMap;

public class WorldManager {
    private static WorldManager global = new WorldManager();

    private HashMap<String, ServerWorld> worlds = new HashMap<>();

    protected WorldManager() {
        refreshWorlds();
    }

    public static WorldManager getGlobalWorldManager() {
        return global;
    }

    public ServerWorld createWorld(String name, WorldOption... options) {
        WorldBuilder.Result result = new WorldBuilder(name).options(options).result();

        WorldCreator creator = new WorldCreator(result.getName());

        if (result.getSeed() != null) {
            creator.seed(result.getSeed());
        }

        creator.generateStructures(result.generateStructures());

        creator.environment(result.getEnvironment());

        if (result.getGenerator() != null) {
            creator.generator(result.getGenerator());
        }

        return worlds.put(name.toLowerCase(), new ServerWorld(creator.createWorld(), this));
    }

    public void refreshWorlds() {
        worlds.clear();

        for (World world : Bukkit.getWorlds()) {
            worlds.put(world.getName().toLowerCase(), new ServerWorld(world, this));
        }
    }

    public ServerWorld getWorldByName(String name) {
        return worlds.get(name.toLowerCase());
    }
}
