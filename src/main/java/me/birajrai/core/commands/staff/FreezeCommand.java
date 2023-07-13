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

import java.util.ArrayList;
import java.util.List;

public class FreezeCommand implements CommandExecutor {

    private final FastCore plugin;

    public FreezeCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.freeze")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.IncorrectUsage")));
            return true;
        }

        if (plugin.frozen_cooldown.contains(((Player) sender).getUniqueId())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.OnCooldown")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (target == null) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.InvalidPlayer")));
            return true;
        }

        plugin.frozen_cooldown.add(((Player) sender).getUniqueId());
        if (!plugin.frozen_players.contains(target.getUniqueId())) {
            plugin.frozen_players.add(target.getUniqueId());

            List<Double> coordinates = new ArrayList<>();
            coordinates.add(target.getLocation().getX());
            coordinates.add(target.getLocation().getZ());

            plugin.frozen_coordinates.put(target.getUniqueId(), coordinates);

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.ExecutorMessageOn")
                    .replace("%target%", yoTarget.getDisplayName())));
            target.sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.TargetMessageOn")));

            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("fcore.staffalerts") && plugin.staff_alerts.contains(staff.getUniqueId()))
                    staff.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.FreezeOn")
                            .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())
                            .replace("%target%", yoTarget.getDisplayName())));
            }
        } else {
            plugin.frozen_players.remove(target.getUniqueId());
            plugin.frozen_coordinates.remove(target.getUniqueId());

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.ExecutorMessageOff")
                    .replace("%target%", yoTarget.getDisplayName())));
            target.sendMessage(Utils.translate(plugin.getConfig().getString("Freeze.TargetMessageOff")));

            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("fcore.staffalerts") && plugin.staff_alerts.contains(staff.getUniqueId()))
                    staff.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.FreezeOff")
                            .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())
                            .replace("%target%", yoTarget.getDisplayName())));
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.frozen_cooldown.remove(((Player) sender).getUniqueId());
            }
        }.runTaskLater(plugin, 20);

        return true;
    }
}
