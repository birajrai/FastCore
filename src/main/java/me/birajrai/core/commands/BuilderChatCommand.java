package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.chats.ChatType;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuilderChatCommand implements CommandExecutor {

    private final FastCore plugin;

    public BuilderChatCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("BuildChat.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.chats.build")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("BuildChat.NoPermission")));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("BuildChat.IncorrectUsage")));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            if (plugin.bchat_toggle.contains(((Player) sender).getUniqueId())) {
                plugin.bchat_toggle.remove(((Player) sender).getUniqueId());
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("BuildChat.ToggleOff")));
            } else {
                plugin.bchat_toggle.add(((Player) sender).getUniqueId());
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("BuildChat.ToggleOn")));
            }

            return true;
        }

        String message = "";
        for (int i = 0; i < args.length; i++) {
            message = message + args[i] + " ";
        }

        for (Player builders : Bukkit.getOnlinePlayers()) {
            if (builders.hasPermission("fcore.chats.build"))
                ChatType.sendMessage((Player) sender, builders, ChatType.BUILD, message);
        }

        return true;
    }
}
