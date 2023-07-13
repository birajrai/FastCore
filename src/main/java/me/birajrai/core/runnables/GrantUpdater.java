package me.birajrai.core.runnables;

import me.birajrai.core.FastCore;
import me.birajrai.core.grants.Grant;
import me.birajrai.core.player.yoPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class GrantUpdater extends BukkitRunnable {

    private final FastCore plugin;

    public GrantUpdater() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public void run() {
        for (String entry : plugin.grantData.config.getKeys(false)) {
            yoPlayer player = new yoPlayer(UUID.fromString(entry));

            if (Grant.getGrants(player).size() <= 0)
                return;

            for (Map.Entry<Integer, Grant> grant : Grant.getGrants(player).entrySet()) {
                if (grant.getValue().getStatus().equalsIgnoreCase("Active")) {
                    if (!(grant.getValue().getDuration() instanceof String)) {
                        if ((long) grant.getValue().getDuration() <= System.currentTimeMillis())
                            grant.getValue().expire();
                    }
                }
            }
        }
    }
}
