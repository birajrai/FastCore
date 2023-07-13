package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearReportsCommand implements CommandExecutor {

    private final FastCore plugin;

    public ClearReportsCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearReports.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.clearreports")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearReports.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearReports.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!plugin.playerData.config.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearReports.InvalidPlayer")));
            return true;
        }

        plugin.playerData.config.set(target.getUniqueId().toString() + ".ReportsAmount", 0);
        plugin.playerData.config.set(target.getUniqueId().toString() + ".Report", null);
        plugin.playerData.saveData();

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearReports.Format")
                .replace("%target%", yoTarget.getDisplayName())));

        return true;
    }
}
