package me.idarkyy.plugincore.packets.actionbar;

import me.idarkyy.plugincore.packets.PacketFunction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActionBar extends PacketFunction {
    private Constructor<?> constructor;
    private Method method;

    public ActionBar() {
        try {
            constructor = getNmsClass("PacketPlayOutChat").getConstructor(getNmsClass("IChatBaseComponent"), byte.class);
            method = getNmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class);
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void send(String message, Player player) {
        if(!getServerVersion().contains("1_8") && !getServerVersion().contains("1_7")) {
        }

        try {
            Object packet = constructor.newInstance(parse(message), (byte) 2);

            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

            playerConnection.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(playerConnection, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Object parse(String message) {
        try {
            return method.invoke(null, "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        } catch(InvocationTargetException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            // ignored
        }

        return null;
    }
}
