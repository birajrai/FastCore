package me.birajrai.core.commands.bungee;

import me.birajrai.core.FastCore;
import me.birajrai.core.management.PlayerManagement;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.server.Server;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final FastCore plugin;
    private final PlayerManagement playerManagement = new PlayerManagement();

    public SpawnCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Spawn.MustBePlayer")));
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Spawn.IncorrectUsage")));
            return true;
        }

        if (args.length == 0) {
            playerManagement.sendToSpawn(Server.getServer((Player) sender), (Player) sender);

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Spawn.TargetMessage")));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Spawn.InvalidPlayer")));
                return true;
            }

            playerManagement.sendToSpawn(Server.getServer(target), target);

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Spawn.ExecutorMessage")
                    .replace("%target%", yoPlayer.getYoPlayer(target).getDisplayName())));

            target.sendMessage(Utils.translate(plugin.getConfig().getString("Spawn.TargetMessage")));
        }

        return true;
    }
}
