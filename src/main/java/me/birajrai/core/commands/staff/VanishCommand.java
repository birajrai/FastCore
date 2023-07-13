package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.server.Server;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private final FastCore plugin;

    public VanishCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.vanish")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.NoPermission")));
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.IncorrectUsage")));
            return true;
        }

        if (args.length == 0) {
            if (!plugin.vanished_players.contains(((Player) sender).getUniqueId())) {
                plugin.vanished_players.add(((Player) sender).getUniqueId());
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.TargetMessageOn")));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.hasPermission("fcore.vanish"))
                        players.hidePlayer((Player) sender);
                }

                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission("fcore.staffalerts") && plugin.staff_alerts.contains(staff.getUniqueId()))
                        staff.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.VanishOnSelf")
                                .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())));
                }

                if (plugin.getConfig().getBoolean("Vanish.FakeLeave")) {
                    for (Player player : Server.getPlayers(Server.getServer((Player) sender)))
                        player.sendMessage(Utils.translate(plugin.getConfig().getString("QuitMessage.Message")
                                .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())));
                }
            } else {
                plugin.vanished_players.remove(((Player) sender).getUniqueId());
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.TargetMessageOff")));
                for (Player players : Bukkit.getOnlinePlayers())
                    ((Player) sender).showPlayer(players);

                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission("fcore.staffalerts") && plugin.staff_alerts.contains(staff.getUniqueId()))
                        staff.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.VanishOffSelf")
                                .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())));
                }

                if (plugin.getConfig().getBoolean("Vanish.FakeJoin")) {
                    for (Player player : Server.getPlayers(Server.getServer((Player) sender)))
                        player.sendMessage(Utils.translate(plugin.getConfig().getString("JoinMessage.Message")
                                .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())));
                }
            }
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            yoPlayer yoTarget = new yoPlayer(target);

            if (target == null) {
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.InvalidPlayer")));
                return true;
            }

            if (!plugin.vanished_players.contains(target.getUniqueId())) {
                plugin.vanished_players.add(target.getUniqueId());
                target.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.TargetMessageOn")));
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.ExecutorMessageOn")
                        .replace("%target%", yoTarget.getDisplayName())));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.hasPermission("fcore.vanish"))
                        players.hidePlayer(target);
                }

                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission("fcore.staffalerts") && plugin.staff_alerts.contains(staff.getUniqueId()))
                        staff.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.VanishOnOther")
                                .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())
                                .replace("%target%", yoTarget.getDisplayName())));
                }

                if (plugin.getConfig().getBoolean("Vanish.FakeLeave")) {
                    for (Player player : Server.getPlayers(Server.getServer(target)))
                        player.sendMessage(Utils.translate(plugin.getConfig().getString("QuitMessage.Message")
                                .replace("%player%", yoTarget.getDisplayName())));
                }
            } else {
                plugin.vanished_players.remove(target.getUniqueId());
                target.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.TargetMessageOff")));
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Vanish.ExecutorMessageOff")
                        .replace("%target%", yoTarget.getDisplayName())));
                for (Player players : Bukkit.getOnlinePlayers())
                    target.showPlayer(players);

                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission("fcore.staffalerts") && plugin.staff_alerts.contains(staff.getUniqueId()))
                        staff.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.VanishOffOther")
                                .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())
                                .replace("%target%", yoTarget.getDisplayName())));
                }

                if (plugin.getConfig().getBoolean("Vanish.FakeJoin")) {
                    for (Player player : Server.getPlayers(Server.getServer(target)))
                        player.sendMessage(Utils.translate(plugin.getConfig().getString("JoinMessage.Message")
                                .replace("%player%", yoTarget.getDisplayName())));
                }
            }
        }

        return true;
    }
}
