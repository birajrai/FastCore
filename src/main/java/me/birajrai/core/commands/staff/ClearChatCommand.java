package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ClearChatCommand implements CommandExecutor {

    private final FastCore plugin;

    public ClearChatCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.clearchat")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ClearChat.NoPermission")));
            return true;
        }

        String executorName;
        if (sender instanceof Player) executorName = yoPlayer.getYoPlayer((Player) sender).getDisplayName();
        else executorName = "&c&lConsole";

        for (Player players : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < 50; i++) {
                players.sendMessage(Utils.translate("&r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n " +
                        "&r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n " +
                        "&r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n &r \n "));
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    players.sendMessage(Utils.translate(plugin.getConfig().getString("ClearChat.MessageAfter")
                            .replace("%executor%", executorName)));
                }
            }.runTaskLater(plugin, 5);
        }

        return true;
    }
}
