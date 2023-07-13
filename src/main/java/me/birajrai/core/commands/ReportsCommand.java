package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.GUI;
import me.birajrai.core.gui.guis.ReportHistoryGUI;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportsCommand implements CommandExecutor {

    private final FastCore plugin;

    public ReportsCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Reports.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.reporthistory")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Reports.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Reports.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!plugin.playerData.config.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Reports.InvalidPlayer")));
            return true;
        }

        ReportHistoryGUI reportHistoryGUI = new ReportHistoryGUI((Player) sender, 18, yoTarget.getDisplayName() + "&a's report history.");
        reportHistoryGUI.setup(target, 1);
        GUI.open(reportHistoryGUI.getGui());

        return true;
    }
}
