package me.idarkyy.plugincore.packets;

import me.idarkyy.plugincore.nms.NmsClass;
import org.bukkit.Bukkit;

public abstract class PacketFunction {
    private static final String SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public NmsClass getNmsClass(String name) {
        return NmsClass.getNmsClass(name);
    }

    public String getServerVersion() {
        return SERVER_VERSION;
    }
}
