package me.birajrai.core.management;

import me.birajrai.core.FastCore;
import me.birajrai.core.grants.Grant;
import me.birajrai.core.player.yoPlayer;
import org.bukkit.OfflinePlayer;

import java.util.Map;

public class GrantManagement {

    private final FastCore plugin;

    public GrantManagement() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    public void setupPlayer(OfflinePlayer target) {
        plugin.grantData.config.set(target.getUniqueId().toString() + ".Grants", null);
        plugin.grantData.saveData();
    }

    public long getGrantDuration(String duration) {
        long ms = (3600 * 1000) + System.currentTimeMillis();

        if (duration.equalsIgnoreCase("10 Seconds"))
            return 1000 * 10 + System.currentTimeMillis();
        if (duration.equalsIgnoreCase("1 Minute"))
            return 1000 * 60 + System.currentTimeMillis();
        if (duration.equalsIgnoreCase("5 Minutes"))
            return (1000 * 60) * 5 + System.currentTimeMillis();
        if (duration.equalsIgnoreCase("30 Minutes"))
            return (1000 * 60) * 30 + System.currentTimeMillis();
        if (duration.equalsIgnoreCase("1 Hour"))
            return (1000 * 60) * 60 + System.currentTimeMillis();
        if (duration.equalsIgnoreCase("3 Hours"))
            return ((1000 * 60) * 60) * 3 + System.currentTimeMillis();
        if (duration.equalsIgnoreCase("1 Day"))
            return ((1000 * 60) * 60) * 24 + System.currentTimeMillis();
        if (duration.equalsIgnoreCase("1 Week"))
            return (((1000 * 60) * 60) * 24) * 7 + System.currentTimeMillis();
        if (duration.equalsIgnoreCase("1 Month"))
            return (((1000 * 60) * 60) * 24) * 31 + System.currentTimeMillis();
        if (duration.equalsIgnoreCase("1 Year"))
            return ((((1000 * 60) * 60) * 24) * 7) * 52 + System.currentTimeMillis();

        return ms;
    }

    public void clearHistory(OfflinePlayer target) {
        for (Map.Entry<Integer, Grant> grant : Grant.getGrants(yoPlayer.getYoPlayer(target)).entrySet())
            Grant.getGrants().remove(grant.getKey());

        setupPlayer(target);

        plugin.grantData.config.set(target.getUniqueId().toString() + ".Grants", null);
        plugin.grantData.saveData();
    }
}
