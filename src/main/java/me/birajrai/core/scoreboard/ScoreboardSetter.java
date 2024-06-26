package me.birajrai.core.scoreboard;

import me.birajrai.core.FastCore;
import me.birajrai.core.management.EconomyManagement;
import me.birajrai.core.management.StatsManagement;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.ranks.Rank;
import me.birajrai.core.server.Server;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScoreboardSetter implements Listener {

    private final FastCore plugin;

    private final EconomyManagement economyManagement = new EconomyManagement();
    private final StatsManagement statsManagement = new StatsManagement();

    public List<UUID> enabled = new ArrayList<>();

    public ScoreboardSetter() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    public static String getEntryFromScore(Objective objective, int score) {
        if (objective == null) return null;
        if (!hasScoreTaken(objective, score)) return null;

        for (String entry : objective.getScoreboard().getEntries()) {
            if (objective.getScore(entry).getScore() == score)
                return objective.getScore(entry).getEntry();
        }

        return null;
    }

    public static boolean hasScoreTaken(Objective objective, int score) {
        for (String entry : objective.getScoreboard().getEntries()) {
            if (objective.getScore(entry).getScore() == score)
                return true;
        }

        return false;
    }

    public static void replaceScore(Objective objective, int score, String name) {
        if (hasScoreTaken(objective, score)) {
            if (getEntryFromScore(objective, score).equalsIgnoreCase(name))
                return;

            if (!getEntryFromScore(objective, score).equalsIgnoreCase(name))
                objective.getScoreboard().resetScores(getEntryFromScore(objective, score));
        }

        objective.getScore(name).setScore(score);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.tsb.contains(event.getPlayer().getUniqueId()))
            return;

        scoreboard(event.getPlayer());
        enabled.add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        enabled.remove(event.getPlayer().getUniqueId());
    }

    public void scoreboard(Player player) {
        if (!plugin.getConfig().getBoolean("Scoreboard.Enabled"))
            return;

        if (enabled.contains(player.getUniqueId()))
            return;

        if (player.getScoreboard().equals(Bukkit.getServer().getScoreboardManager().getMainScoreboard()))
            player.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());

        yoPlayer yoPlayer = new yoPlayer(player);

        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = (scoreboard.getObjective(player.getName()) == null) ? scoreboard.registerNewObjective(player.getName(), "dummy") : scoreboard.getObjective(player.getName());

        if (plugin.tsb.contains(player.getUniqueId()))
            return;

        Server server = Server.getServer(player);

        Rank rank = yoPlayer.getRank();
        if (plugin.rank_disguise.containsKey(player.getUniqueId()))
            rank = yoPlayer.getRankDisguise();

        String bounty;
        if (economyManagement.isBountied(server, player))
            bounty = String.valueOf(economyManagement.getBountyAmount(server, player));
        else bounty = "&cNot Bountied";

        int staffAmount = 0;
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("yocore.chats.staff"))
                staffAmount++;
        }

        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");

        if (plugin.getConfig().getBoolean("Scoreboard.ModMode.Enabled") && plugin.modmode_players.contains(player.getUniqueId())) {
            objective.setDisplayName(Utils.translate(plugin.getConfig().getString("Scoreboard.ModMode.Title")));

            int row = plugin.getConfig().getStringList("Scoreboard.ModMode.Board").size();
            for (String score : plugin.getConfig().getStringList("Scoreboard.ModMode.Board")) {
                row--;
                String rowFormat = score
                        .replace("%online%", df.format(Bukkit.getOnlinePlayers().size() - plugin.vanished_players.size()))
                        .replace("%rank%", rank.getDisplay())
                        .replace("%kills%", df.format(statsManagement.getKills(server, player)))
                        .replace("%deaths%", df.format(statsManagement.getDeaths(server, player)))
                        .replace("%kdr%", df.format(statsManagement.getKDR(server, player)))
                        .replace("%streak%", df.format(statsManagement.getStreak(server, player)))
                        .replace("%balance%", df.format(economyManagement.getMoney(server, player)))
                        .replace("%bounty%", bounty)
                        .replace("%vanish%", String.valueOf(plugin.vanished_players.contains(player.getUniqueId())))
                        .replace("%online_staff%", df.format(staffAmount)
                                .replace("%player%", yoPlayer.getDisplayName()));
                replaceScore(objective, row, Utils.translate(rowFormat));
            }
        } else {
            if (plugin.getConfig().getBoolean("Scoreboard.Global.Enabled")) {
                objective.setDisplayName(Utils.translate(plugin.getConfig().getString("Scoreboard.Global.Title")));

                int row = plugin.getConfig().getStringList("Scoreboard.Global.Board").size();
                for (String score : plugin.getConfig().getStringList("Scoreboard.Global.Board")) {
                    row--;
                    String rowFormat = score
                            .replace("%online%", df.format(Bukkit.getOnlinePlayers().size() - plugin.vanished_players.size()))
                            .replace("%rank%", rank.getDisplay())
                            .replace("%kills%", df.format(statsManagement.getKills(server, player)))
                            .replace("%deaths%", df.format(statsManagement.getDeaths(server, player)))
                            .replace("%kdr%", df.format(statsManagement.getKDR(server, player)))
                            .replace("%streak%", df.format(statsManagement.getStreak(server, player)))
                            .replace("%balance%", df.format(economyManagement.getMoney(server, player)))
                            .replace("%bounty%", bounty)
                            .replace("%vanish%", String.valueOf(plugin.vanished_players.contains(player.getUniqueId())))
                            .replace("%online_staff%", df.format(staffAmount))
                            .replace("%player%", yoPlayer.getDisplayName());
                    replaceScore(objective, row, Utils.translate(rowFormat));
                }
            } else {
                for (String world : plugin.getConfig().getConfigurationSection("Scoreboard.Worlds").getKeys(false)) {
                    if (world.equalsIgnoreCase(player.getWorld().getName())) {
                        objective.setDisplayName(Utils.translate(plugin.getConfig().getString("Scoreboard.Worlds." + world + ".Title")));

                        int row = plugin.getConfig().getStringList("Scoreboard.Worlds." + world + ".Board").size();
                        for (String score : plugin.getConfig().getStringList("Scoreboard.Worlds." + world + ".Board")) {
                            row--;
                            String rowFormat = score
                                    .replace("%online%", df.format(Bukkit.getOnlinePlayers().size() - plugin.vanished_players.size()))
                                    .replace("%rank%", rank.getDisplay())
                                    .replace("%kills%", df.format(statsManagement.getKills(server, player)))
                                    .replace("%deaths%", df.format(statsManagement.getDeaths(server, player)))
                                    .replace("%kdr%", df.format(statsManagement.getKDR(server, player)))
                                    .replace("%streak%", df.format(statsManagement.getStreak(server, player)))
                                    .replace("%balance%", df.format(economyManagement.getMoney(server, player)))
                                    .replace("%bounty%", bounty)
                                    .replace("%vanish%", String.valueOf(plugin.vanished_players.contains(player.getUniqueId())))
                                    .replace("%online_staff%", df.format(staffAmount))
                                    .replace("%player%", yoPlayer.getDisplayName());
                            replaceScore(objective, row, Utils.translate(rowFormat));
                        }
                    }
                }
            }
        }

        if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR)
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        player.setScoreboard(scoreboard);
    }
}