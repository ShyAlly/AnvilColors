package com.jeff_media.anvilcolors;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class AnvilListener implements Listener {

    private final AnvilColors plugin;
    private final Formatter formatter;

    public AnvilListener(AnvilColors plugin) {
        this.plugin = plugin;
        this.formatter = new Formatter(plugin);
    }

    @EventHandler
    public void onAnvilRename(PrepareAnvilEvent event) {
        List<HumanEntity> viewers = event.getViewers();
        if(viewers.size() != 1) return;
        HumanEntity humanEntity = viewers.get(0);
        if(!(humanEntity instanceof Player)) return;

        Player player = (Player) humanEntity;
        ItemStack item = event.getResult();

        if(item == null) return;
        if(!item.hasItemMeta()) return;
        ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        if(!meta.hasDisplayName()) return;

        String displayName = meta.getDisplayName();
        displayName = formatter.colorize(player, displayName, plugin.getItalicsMode());
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        event.setResult(item);
    }
}
