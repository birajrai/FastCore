package me.birajrai.core.runnables;

import me.birajrai.core.FastCore;
import me.birajrai.core.server.Server;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VanishUpdater extends BukkitRunnable {

    private final FastCore plugin;

    public VanishUpdater() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public void run() {
        for (Player staff : Bukkit.getOnlinePlayers()) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (plugin.vanished_players.contains(staff.getUniqueId())) {
                    if (!players.hasPermission("fcore.vanish"))
                        players.hidePlayer(staff);
                    else {
                        if (Server.getServer(staff) == Server.getServer(players))
                            players.showPlayer(staff);
                    }
                }
            }
        }
    }
}
