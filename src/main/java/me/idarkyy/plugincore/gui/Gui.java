package me.idarkyy.plugincore.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class Gui implements Listener {
    private static final ItemStack AIR = new ItemStack(Material.AIR);

    private Inventory inventory;
    private Plugin plugin;

    private HashMap<Integer, BiConsumer<Player, ItemStack>> items = new HashMap<>();


    public Gui(Plugin plugin, int rows) {
        this(plugin, rows, "");
    }

    public Gui(Plugin plugin, int rows, String title) {
        inventory = Bukkit.createInventory(null, rows, ChatColor.translateAlternateColorCodes('&', title));
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onClick(InventoryClickEvent event) {
                if (event.getClickedInventory() == inventory) {
                    trigger(event.getSlot(), (Player) event.getWhoClicked(), event.getCurrentItem());
                    event.setCancelled(true);
                }
            }
        }, plugin);
    }

    public void add(int slot, ItemStack item, BiConsumer<Player, ItemStack> action) {
        items.put(slot, action);
        inventory.setItem(slot, item);
    }

    public void add(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    public void remove(int slot) {
        items.remove(slot);
        inventory.setItem(slot, AIR);
    }


    public void trigger(int slot, Player player, ItemStack item) {
        if (items.containsKey(slot)) {
            items.get(slot).accept(player, item);
        }
    }
}
