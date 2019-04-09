package me.idarkyy.plugincore.listener;

import me.idarkyy.plugincore.events.PlayerMoveByBlockEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class GlobalListener implements org.bukkit.event.Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(event.getFrom().getBlockX() != event.getTo().getBlockX()
                || event.getFrom().getBlockY() != event.getTo().getBlockY()
                || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {

            PlayerMoveByBlockEvent evt = new PlayerMoveByBlockEvent(event.getPlayer(), event.getFrom(), event.getTo());

            Bukkit.getPluginManager().callEvent(event);

            event.setCancelled(evt.isCancelled());
        }
    }
}
