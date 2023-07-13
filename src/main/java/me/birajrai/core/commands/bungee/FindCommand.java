package me.birajrai.core.commands.bungee;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.server.Server;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FindCommand implements CommandExecutor {

    private final FastCore plugin;

    public FindCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.find")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Find.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Find.IncorrectUsage")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Find.InvalidPlayer")));
            return true;
        }

        Server server = Server.getServer((Player) sender);

        if (server == null) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Find.InvalidPlayer")));
            return true;
        }

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("Find.Format")
                .replace("%target%", yoPlayer.getYoPlayer(target).getDisplayName())
                .replace("%server%", server.getName())));

        return true;
    }
}
