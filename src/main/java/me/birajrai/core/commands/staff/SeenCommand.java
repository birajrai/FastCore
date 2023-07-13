package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.ranks.Rank;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SeenCommand implements CommandExecutor {

    private final FastCore plugin;

    public SeenCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.seen")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Seen.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Seen.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!plugin.playerData.config.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Seen.InvalidPlayer")));
            return true;
        }

        String ip;
        if (sender.hasPermission("fcore.seen.ip"))
            ip = yoTarget.getIP();
        else ip = "Hidden";
        Rank rank = yoTarget.getRank();
        String playTime = yoTarget.getPlayTime();
        String allIPsMessage = "";
        if (sender.hasPermission("fcore.seen.ip")) {
            for (String entry : yoTarget.getAllIPs()) {
                if (allIPsMessage.equalsIgnoreCase("")) allIPsMessage = "&7- " + entry;
                else allIPsMessage = allIPsMessage + "\n&7- " + entry;
            }
        } else allIPsMessage = "Hidden";

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("Seen.Format")
                .replace("%target%", yoTarget.getDisplayName())
                .replace("%name%", target.getName())
                .replace("%rank%", rank.getDisplay())
                .replace("%ip%", ip)
                .replace("%firstjoined%", Utils.getExpirationDate(yoTarget.getFirstJoined()))
                .replace("%all_ips%", allIPsMessage)
                .replace("%playtime%", playTime)));

        return true;
    }
}
