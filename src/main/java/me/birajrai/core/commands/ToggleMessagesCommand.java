package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleMessagesCommand implements CommandExecutor {

    private final FastCore plugin;

    public ToggleMessagesCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Message.MustBePlayer")));
            return true;
        }

        if (!plugin.message_toggled.contains(((Player) sender).getUniqueId())) {
            plugin.message_toggled.add(((Player) sender).getUniqueId());

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Message.ToggleOff")));
        } else {
            plugin.message_toggled.remove(((Player) sender).getUniqueId());

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Message.ToggleOn")));
        }

        return true;
    }
}
