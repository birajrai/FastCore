package me.birajrai.core.commands.punishments;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.punishments.Punishment;
import me.birajrai.core.punishments.PunishmentType;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BlacklistCommand implements CommandExecutor {

    private final FastCore plugin;

    public BlacklistCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.blacklist")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Blacklist.NoPermission")));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Blacklist.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!plugin.playerData.config.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Blacklist.InvalidPlayer")));
            return true;
        }

        if (plugin.blacklisted_players.containsKey(target.getUniqueId())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Blacklist.TargetIsBlacklisted")));
            return true;
        }

        String reason = "";
        for (int i = 1; i < args.length; i++) {
            reason = reason + args[i] + " ";
        }

        String executor;
        String executorName;
        if (!(sender instanceof Player)) {
            executor = "CONSOLE";
            executorName = "&c&lConsole";
        } else {
            executor = ((Player) sender).getUniqueId().toString();
            executorName = yoPlayer.getYoPlayer((Player) sender).getDisplayName();
        }

        boolean silent = false;
        if (reason.contains("-s")) {
            reason = reason.replace("-s ", "");
            silent = true;
        }

        Punishment punishment = new Punishment(PunishmentType.BLACKLIST, yoTarget, executor, "Permanent", silent, reason);
        punishment.create();
        punishment.execute();

        if (silent) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("SilentPrefix") + plugin.getConfig().getString("Blacklist.ExecutorMessage")
                    .replace("%target%", yoTarget.getDisplayName())
                    .replace("%reason%", reason)));
        } else {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Blacklist.ExecutorMessage")
                    .replace("%target%", yoTarget.getDisplayName())
                    .replace("%reason%", reason)));
        }

        if (target.isOnline()) {
            Bukkit.getPlayer(target.getName()).kickPlayer(Utils.translate(plugin.getConfig().getString("Blacklist.TargetMessage")
                    .replace("%reason%", reason)));
        }

        for (Player players : Bukkit.getOnlinePlayers()) {
            yoPlayer yoPlayer = new yoPlayer(players);

            if (players.getAddress().getAddress().getHostAddress().equals(yoTarget.getIP())
                    || (yoTarget.getAllIPs().contains(yoPlayer.getIP())
                    || yoPlayer.getAllIPs().contains(yoTarget.getIP()))) {
                players.kickPlayer(Utils.translate(plugin.getConfig().getString("Blacklist.TargetMessage")
                        .replace("%reason%", reason)));
            }
        }

        for (String players : plugin.playerData.config.getKeys(false)) {
            yoPlayer yoPlayer = new yoPlayer(UUID.fromString(players));

            if (yoPlayer.getIP().equalsIgnoreCase(yoTarget.getIP())
                    || (yoTarget.getAllIPs().contains(yoPlayer.getIP())
                    || yoPlayer.getAllIPs().contains(yoTarget.getIP()))) {
                if (!UUID.fromString(players).equals(target.getUniqueId())) {
                    Punishment linkedPunishment = new Punishment(PunishmentType.BLACKLIST, yoPlayer, executor, "Permanent", silent, reason + " (Linked to " + target.getName() + ")");
                    linkedPunishment.create();
                    linkedPunishment.execute();
                }
            }
        }

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (silent) {
                if (players.hasPermission("fcore.silent")) {
                    players.sendMessage(Utils.translate(plugin.getConfig().getString("SilentPrefix") + plugin.getConfig().getString("Blacklist.BroadcastMessage")
                            .replace("%executor%", executorName)
                            .replace("%target%", yoTarget.getDisplayName())));
                }
            } else {
                players.sendMessage(Utils.translate(plugin.getConfig().getString("Blacklist.BroadcastMessage")
                        .replace("%executor%", executorName)
                        .replace("%target%", yoTarget.getDisplayName())));
            }
        }

        return true;
    }
}
