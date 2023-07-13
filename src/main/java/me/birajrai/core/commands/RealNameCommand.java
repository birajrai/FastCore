package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.UUID;

public class RealNameCommand implements CommandExecutor {

    private final FastCore plugin;

    public RealNameCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("RealName.IncorrectUsage")));
            return true;
        }

        if (!plugin.nickname.containsValue(args[0].replace("&", ""))) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("RealName.InvalidNick")));
            return true;
        }

        OfflinePlayer target;
        yoPlayer yoTarget = null;

        for (Map.Entry<UUID, String> iterator : plugin.nickname.entrySet()) {
            if (plugin.nickname.get(iterator.getKey()).equalsIgnoreCase(iterator.getValue())) {
                target = Bukkit.getOfflinePlayer(iterator.getKey());
                yoTarget = new yoPlayer(target);
            }
        }

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("RealName.Format")
                .replace("%target%", yoTarget.getDisplayName())
                .replace("%nickname%", yoTarget.getNickname())));

        return true;
    }
}
