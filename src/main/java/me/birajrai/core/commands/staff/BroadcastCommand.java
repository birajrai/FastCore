package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {

    private final FastCore plugin;

    public BroadcastCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String display;
        if (sender instanceof Player) display = yoPlayer.getYoPlayer((Player) sender).getDisplayName();
        else display = "&c&lConsole";

        if (!sender.hasPermission("fcore.broadcast")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Broadcast.NoPermission")));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Broadcast.IncorrectUsage")));
            return true;
        }

        String message = "";
        for (int i = 0; i < args.length; i++) {
            if (message.equalsIgnoreCase("")) message = args[i];
            else message = message + " " + args[i];
        }

        for (Player players : Bukkit.getOnlinePlayers())
            players.sendMessage(Utils.translate(plugin.getConfig().getString("Broadcast.Message")
                    .replace("%message%", message)));

        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("fcore.staffalerts") && plugin.staff_alerts.contains(staff.getUniqueId()))
                staff.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.Broadcast")
                        .replace("%player%", display)
                        .replace("%message%", message)));
        }

        return true;
    }
}
