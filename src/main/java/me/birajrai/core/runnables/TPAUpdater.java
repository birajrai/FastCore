package me.birajrai.core.runnables;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TPAUpdater extends BukkitRunnable {

    private final FastCore plugin;

    public TPAUpdater() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (plugin.tpa.containsKey(players.getUniqueId())
                    && plugin.tpa_timer.containsKey(players.getUniqueId())
                    && plugin.tpa_coords.containsKey(players.getUniqueId())) {
                if (plugin.tpa_timer.get(players.getUniqueId()) <= 0) {
                    players.teleport(Bukkit.getPlayer(plugin.tpa.get(players.getUniqueId())).getLocation());

                    players.sendMessage(Utils.translate(plugin.getConfig().getString("Teleport.TeleportRequestTeleported")
                            .replace("%target%", new yoPlayer(Bukkit.getPlayer(plugin.tpa.get(players.getUniqueId()))).getDisplayName())));

                    plugin.tpa_timer.remove(players.getUniqueId());
                    plugin.tpa_coords.remove(players.getUniqueId());
                    plugin.tpa.remove(players.getUniqueId());
                } else plugin.tpa_timer.put(players.getUniqueId(), plugin.tpa_timer.get(players.getUniqueId()) - 1);
            }
        }
    }
}
