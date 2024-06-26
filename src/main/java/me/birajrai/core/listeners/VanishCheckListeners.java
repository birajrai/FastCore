package me.birajrai.core.listeners;

import me.birajrai.core.FastCore;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class VanishCheckListeners implements Listener {

    private final FastCore plugin;

    public VanishCheckListeners() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (plugin.vanished_players.contains(event.getEntity().getUniqueId()))
                event.setCancelled(true);
        }

        if (event.getDamager() instanceof Player) {
            if (plugin.vanished_players.contains(event.getDamager().getUniqueId()))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        for (LivingEntity splashed : event.getAffectedEntities()) {
            if (splashed instanceof Player) {
                if (plugin.vanished_players.contains(splashed.getUniqueId()))
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMobNotice(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            if (plugin.vanished_players.contains(event.getTarget().getUniqueId())) {
                event.setTarget(null);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (plugin.vanished_players.contains(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }
}
