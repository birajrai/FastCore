package me.birajrai.core.runnables;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.punishments.Punishment;
import me.birajrai.core.punishments.PunishmentType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class BanUpdater extends BukkitRunnable {

    private final FastCore plugin;

    public BanUpdater() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public void run() {
        if (plugin.punishmentData.config.contains("BannedPlayers")) {
            for (String entry : plugin.punishmentData.config.getConfigurationSection("BannedPlayers").getKeys(false)) {
                yoPlayer player = new yoPlayer(UUID.fromString(entry));

                if (Punishment.getPunishments(player).size() <= 0)
                    return;

                for (Map.Entry<Integer, Punishment> punishment : Punishment.getPunishments(player).entrySet()) {
                    if (punishment.getValue().getType() == PunishmentType.BAN) {
                        if (punishment.getValue().getStatus().equalsIgnoreCase("Active")) {
                            if (!(punishment.getValue().getDuration() instanceof String)) {
                                if ((long) punishment.getValue().getDuration() <= System.currentTimeMillis())
                                    punishment.getValue().expire();
                            }
                        }
                    }
                }
            }
        }
    }
}
