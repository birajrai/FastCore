package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

    private final FastCore plugin;

    public PingCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Ping.MustBePlayer")));
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Ping.IncorrectUsage")));
            return true;
        }

        Player target;
        String ping;

        if (args.length == 0) {
            target = (Player) sender;
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Ping.InvalidPlayer")));
                return true;
            }
        }

        yoPlayer yoTarget = new yoPlayer(target);
        ping = String.valueOf(yoTarget.getPing());

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("Ping.Format")
                .replace("%player%", yoTarget.getDisplayName())
                .replace("%ping%", ping)));

        return true;
    }
}
