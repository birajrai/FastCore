package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.chats.ChatType;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManagementChatCommand implements CommandExecutor {

    private final FastCore plugin;

    public ManagementChatCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ManagementChat.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.chats.management")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ManagementChat.NoPermission")));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ManagementChat.IncorrectUsage")));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            if (plugin.mchat_toggle.contains(((Player) sender).getUniqueId())) {
                plugin.mchat_toggle.remove(((Player) sender).getUniqueId());
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("ManagementChat.ToggleOff")));
            } else {
                plugin.mchat_toggle.add(((Player) sender).getUniqueId());
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("ManagementChat.ToggleOn")));
            }

            return true;
        }

        String message = "";
        for (int i = 0; i < args.length; i++) {
            message = message + args[i] + " ";
        }

        for (Player managers : Bukkit.getOnlinePlayers()) {
            if (managers.hasPermission("fcore.chats.management"))
                ChatType.sendMessage((Player) sender, managers, ChatType.MANAGEMENT, message);
        }

        return true;
    }
}
