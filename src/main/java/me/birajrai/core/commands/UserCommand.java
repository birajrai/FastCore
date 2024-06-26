package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class UserCommand implements CommandExecutor {

    private final FastCore plugin;

    public UserCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.user")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.NoPermission")));
            return true;
        }

        if (args.length < 2 || args.length > 3) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!plugin.playerData.config.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.InvalidPlayer")));
            return true;
        }

        if (!args[1].equalsIgnoreCase("add")
                && !args[1].equalsIgnoreCase("remove")
                && !args[1].equalsIgnoreCase("list")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.IncorrectUsage")));
            return true;
        }

        switch (args[1].toLowerCase()) {
            case "add":
                if (args.length != 3) {
                    sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.IncorrectUsage")));
                    return true;
                }

                sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.PermissionAdded")
                        .replace("%target%", yoTarget.getDisplayName())
                        .replace("%permission%", args[2])));

                yoTarget.permissions().add(args[2].toLowerCase());

                break;
            case "remove":
                if (args.length != 3) {
                    sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.IncorrectUsage")));
                    return true;
                }

                sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.PermissionRemoved")
                        .replace("%target%", yoTarget.getDisplayName())
                        .replace("%permission%", args[2])));

                yoTarget.permissions().remove(args[2].toLowerCase());

                break;
            case "list":
                if (args.length != 2) {
                    sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.IncorrectUsage")));
                    return true;
                }

                List<String> player_permissions = plugin.permissionsData.config.getStringList("Players." + target.getUniqueId().toString() + ".Permissions");

                String permissions = "";
                for (String permission : player_permissions) {
                    if (permissions.equalsIgnoreCase("")) permissions = "&7 - " + permission;
                    else permissions = permissions + "\n&7 - " + permission;
                }

                sender.sendMessage(Utils.translate(plugin.getConfig().getString("UserCommand.PlayerPermissions")
                        .replace("%target%", yoTarget.getDisplayName())
                        .replace("%permissions%", permissions)));

                break;
        }

        return true;
    }
}
