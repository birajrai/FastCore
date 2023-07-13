package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.GUI;
import me.birajrai.core.gui.guis.GrantGUI;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GrantCommand implements CommandExecutor {

    private final FastCore plugin;

    public GrantCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Grant.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.grant")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Grant.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Grant.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!plugin.playerData.config.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Grant.InvalidPlayer")));
            return true;
        }

        plugin.grant_player.remove(((Player) sender).getUniqueId());
        plugin.grant_grant.remove(((Player) sender).getUniqueId());
        plugin.grant_type.remove(((Player) sender).getUniqueId());
        plugin.grant_reason.remove(((Player) sender).getUniqueId());
        plugin.grant_duration.remove(((Player) sender).getUniqueId());

        GrantGUI grantGUI = new GrantGUI((Player) sender, 18, "&aSelect a grant.");
        grantGUI.setup((Player) sender, target, 1);
        GUI.open(grantGUI.getGui());

        plugin.grant_player.put(((Player) sender).getUniqueId(), target.getUniqueId());

        return true;
    }
}