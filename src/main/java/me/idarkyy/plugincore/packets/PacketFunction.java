package me.idarkyy.plugincore.packets;

import org.bukkit.Bukkit;

import java.util.HashMap;

public class PacketFunction {
    private static HashMap<String, Class<?>> cache = new HashMap<>();

    public Class<?> getNmsClass(String name) {
        String key = "net.minecraft.server." + getServerVersion() + "." + name;

        if(cache.containsKey(key)) {
            return cache.get(key);
        }

        try {
            return cache.put(key, Class.forName(key));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
