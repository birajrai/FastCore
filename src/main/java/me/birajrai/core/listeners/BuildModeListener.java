package me.birajrai.core.listeners;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildModeListener implements Listener {

    private final FastCore plugin;

    public BuildModeListener() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent event) {
        if (plugin.buildmode_players.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.translate(plugin.getConfig().getString("BuildMode.BlockPlace")));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (plugin.buildmode_players.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.translate(plugin.getConfig().getString("BuildMode.BlockPlace")));
        }
    }
}
