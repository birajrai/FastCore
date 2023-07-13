package me.birajrai.core.management;

import me.birajrai.core.FastCore;
import me.birajrai.core.ranks.Rank;
import me.birajrai.core.server.Server;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerManagement {

    private FastCore plugin;

    public PlayerManagement() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    public void setupPlayer(Player player) {
        plugin.playerData.config.set(player.getUniqueId().toString() + ".Name", player.getName());
        plugin.playerData.config.set(player.getUniqueId().toString() + ".Rank", "DEFAULT");
        for (Map.Entry<String, Rank> ranks : Rank.getRanks().entrySet()) {
            if (ranks.getValue().isDefault())
                plugin.playerData.config.set(player.getUniqueId().toString() + ".Rank", ranks.getValue().getID());
        }
        plugin.playerData.config.set(player.getUniqueId().toString() + ".IP", player.getAddress().getAddress().getHostAddress());
        plugin.playerData.config.set(player.getUniqueId().toString() + ".ReportsAmount", 0);
        plugin.playerData.config.set(player.getUniqueId().toString() + ".FirstJoined", System.currentTimeMillis());

        List<String> totalIPs = new ArrayList<>();
        totalIPs.add(player.getAddress().getAddress().getHostAddress());
        plugin.playerData.config.set(player.getUniqueId().toString() + ".TotalIPs", totalIPs);

        plugin.playerData.saveData();
    }

    public int getReportsAmount(OfflinePlayer target) {
        return plugin.playerData.config.getInt(target.getUniqueId().toString() + ".ReportsAmount");
    }

    public void addReport(OfflinePlayer target, String executor, String reason, long date) {
        int ID = getReportsAmount(target) + 1;

        plugin.playerData.config.set(target.getUniqueId().toString() + ".ReportsAmount", ID);
        plugin.playerData.config.set(target.getUniqueId().toString() + ".Report." + ID + ".Executor", executor);
        plugin.playerData.config.set(target.getUniqueId().toString() + ".Report." + ID + ".Reason", reason);
        plugin.playerData.config.set(target.getUniqueId().toString() + ".Report." + ID + ".Date", date);

        plugin.playerData.saveData();
    }

    public void sendToSpawn(Server server, Player player) {
        Location location = server.getSpawn();

        player.teleport(location);
    }
}
