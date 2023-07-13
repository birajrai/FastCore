package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.management.GrantManagement;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClearGrantHistoryCommand implements CommandExecutor {

    private final FastCore plugin;
    private final GrantManagement grantManagement = new GrantManagement();

    public ClearGrantHistoryCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.cleargranthistory")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearGrantHistory.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearGrantHistory.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!plugin.playerData.config.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearGrantHistory.InvalidPlayer")));
            return true;
        }

        grantManagement.clearHistory(target);
        sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearGrantHistory.ExecutorMessage")
                .replace("%target%", yoTarget.getDisplayName())));

        return true;
    }
}
