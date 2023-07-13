package me.birajrai.core.commands.stats.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.management.StatsManagement;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.server.Server;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetStatsCommand implements CommandExecutor {

    private final FastCore plugin;
    private final StatsManagement statsManagement = new StatsManagement();

    public ResetStatsCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ResetStats.MustBePlayer")));
            return true;
        }

        Server server = Server.getServer((Player) sender);

        if (!statsManagement.statsAreEnabled(server)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Stats.NotEnabledMessage")));
            return true;
        }

        if (!sender.hasPermission("fcore.resetstats")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ResetStats.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ResetStats.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!statsManagement.isInitialized(target)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ResetStats.InvalidPlayer")));
            return true;
        }

        statsManagement.resetPlayer(server, target);

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("ResetStats.Format")
                .replace("%target%", yoTarget.getDisplayName())));

        return true;
    }
}