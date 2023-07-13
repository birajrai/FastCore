package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildModeCommand implements CommandExecutor {

    private final FastCore plugin;

    public BuildModeCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("BuildMode.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.buildmode")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("BuildMode.NoPermission")));
            return true;
        }

        if (plugin.buildmode_players.contains(((Player) sender).getUniqueId())) {
            plugin.buildmode_players.remove(((Player) sender).getUniqueId());

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("BuildMode.MessageOn")));
        } else {
            plugin.buildmode_players.add(((Player) sender).getUniqueId());

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("BuildMode.MessageOff")));
        }

        return true;
    }
}
