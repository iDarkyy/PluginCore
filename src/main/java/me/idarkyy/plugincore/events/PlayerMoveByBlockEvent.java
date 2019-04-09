package me.idarkyy.plugincore.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveByBlockEvent extends Event implements Cancellable {
    private static HandlerList handlerList = new HandlerList();

    private boolean cancel;

    private Player player;
    private Location from, to;

    public PlayerMoveByBlockEvent(Player player, Location from, Location to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getFrom() {
        return from.getBlock().getLocation();
    }

    public Location getTo() {
        return to.getBlock().getLocation();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
