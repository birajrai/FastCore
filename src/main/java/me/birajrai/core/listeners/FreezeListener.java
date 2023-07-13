package me.birajrai.core.listeners;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class FreezeListener implements Listener {

    private final FastCore plugin;

    public FreezeListener() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!plugin.frozen_players.contains(event.getPlayer().getUniqueId()))
            return;

        List<Double> coordinates = plugin.frozen_coordinates.get(event.getPlayer().getUniqueId());
        Location location = new Location(event.getPlayer().getWorld(), coordinates.get(0), event.getPlayer().getLocation().getY(), coordinates.get(1), event.getPlayer().getLocation().getYaw(), event.getPlayer().getLocation().getPitch());

        if (event.getPlayer().getLocation().getX() != coordinates.get(0)) {
            event.getPlayer().teleport(location);
            event.getPlayer().sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.TargetMessageOn")));
        }

        if (event.getPlayer().getLocation().getZ() != coordinates.get(1)) {
            event.getPlayer().teleport(location);
            event.getPlayer().sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.TargetMessageOn")));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (plugin.frozen_players.contains(event.getEntity().getUniqueId()))
            event.setCancelled(true);
    }
}
