package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteChatCommand implements CommandExecutor {

    private final FastCore plugin;

    public MuteChatCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.mutechat")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("MuteChat.NoPermission")));
            return true;
        }

        String executorName;
        if (sender instanceof Player) executorName = yoPlayer.getYoPlayer((Player) sender).getDisplayName();
        else executorName = "&c&lConsole";


        if (plugin.chat_muted) {
            plugin.chat_muted = false;
            for (Player players : Bukkit.getOnlinePlayers())
                players.sendMessage(Utils.translate(plugin.getConfig().getString("MuteChat.MessageOff")
                        .replace("%executor%", executorName)));
        } else {
            plugin.chat_muted = true;
            for (Player players : Bukkit.getOnlinePlayers())
                players.sendMessage(Utils.translate(plugin.getConfig().getString("MuteChat.MessageOn")
                        .replace("%executor%", executorName)));
        }

        return true;
    }
}
