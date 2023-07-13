package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.GUI;
import me.birajrai.core.gui.guis.GrantsGUI;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GrantsCommand implements CommandExecutor {

    private final FastCore plugin;

    public GrantsCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("GrantHistory.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.granthistory")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("GrantHistory.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("GrantHistory.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!plugin.playerData.config.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("GrantHistory.InvalidPlayer")));
            return true;
        }

        plugin.selected_grant_history.remove(((Player) sender).getUniqueId());

        GrantsGUI grantsGUI = new GrantsGUI((Player) sender, 18, yoTarget.getDisplayName() + "&a's grant history.");
        grantsGUI.setup((Player) sender, target, 1);
        GUI.open(grantsGUI.getGui());

        plugin.selected_grant_history.put(((Player) sender).getUniqueId(), target.getUniqueId());

        return true;
    }
}
