package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    private final FastCore plugin;

    public FeedCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Feed.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.feed")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Feed.NoPermission")));
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Feed.IncorrectUsage")));
            return true;
        }

        if (args.length == 0) {
            ((Player) sender).setFoodLevel(20);

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Feed.TargetMessage")));

            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("fcore.staffalerts") && plugin.staff_alerts.contains(staff.getUniqueId()))
                    staff.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.FeedSelf")
                            .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())));
            }
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            yoPlayer yoTarget = new yoPlayer(target);

            if (target == null) {
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Feed.InvalidPlayer")));
                return true;
            }

            target.setFoodLevel(20);

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Feed.ExecutorMessage")
                    .replace("%target%", yoTarget.getDisplayName())));
            target.sendMessage(Utils.translate(plugin.getConfig().getString("Feed.TargetMessage")));

            for (Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("fcore.staffalerts") && plugin.staff_alerts.contains(staff.getUniqueId()))
                    staff.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.FeedOther")
                            .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())
                            .replace("%target%", yoTarget.getDisplayName())));
            }
        }

        return true;
    }
}
