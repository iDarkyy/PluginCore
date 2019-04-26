package me.idarkyy.plugincore.worldmanager.world;

import me.idarkyy.plugincore.worldmanager.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.util.List;

public class ServerWorld {
    private World world;
    private WorldManager worldManager;

    private File file;
    private String name;

    public ServerWorld(World world, WorldManager worldManager) {
        this.world = world;
        this.worldManager = worldManager;

        this.file = world.getWorldFolder();
        this.name = world.getName();
    }

    public void load() {
        Bukkit.createWorld(new WorldCreator(name));
    }

    public void delete() {
        Bukkit.unloadWorld(world, false);
        file.delete();

        world = null;
        file = null;
    }

    public void unload(boolean save) {
        Bukkit.unloadWorld(world, save);
    }

    public void unload() {
        unload(true);
    }

    public void copyTo(File directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Inputted argument is not a directory");
        }

        if (directory.exists()) {
            throw new IllegalArgumentException("The inputted directory must not exist");
        }

        FileUtil.copy(file, directory);
    }

    public long getSeed() {
        return world.getSeed();
    }

    public List<Player> getPlayers() {
        return world.getPlayers();
    }

    protected World getWorld() {
        return world;
    }
}
