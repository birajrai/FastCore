package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {

    private final FastCore plugin;

    public SpeedCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Speed.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.speed")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Speed.NoPermission")));
            return true;
        }

        if (args.length < 1 || args.length > 2) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Speed.IncorrectUsage")));
            return true;
        }

        try {
            Float.parseFloat(args[0]);
        } catch (NumberFormatException exception) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Speed.InvalidSpeed")));
            return true;
        }

        if (Float.parseFloat(args[0]) < -1 || Float.parseFloat(args[0]) > 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Speed.InvalidSpeed")));
            return true;
        }

        if (args.length == 1) {
            if (((Player) sender).isFlying()) ((Player) sender).setFlySpeed(Float.parseFloat(args[0]));
            else ((Player) sender).setWalkSpeed(Float.parseFloat(args[0]));

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Speed.TargetMessage")
                    .replace("%speed%", args[0])));
        } else {
            Player target = Bukkit.getPlayer(args[1]);
            yoPlayer yoTarget = new yoPlayer(target);

            if (target == null) {
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Speed.InvalidPlayer")));
                return true;
            }

            if (target.isFlying()) target.setFlySpeed(Float.parseFloat(args[0]));
            else target.setWalkSpeed(Float.parseFloat(args[0]));

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Speed.ExecutorMessage")
                    .replace("%speed%", args[0])
                    .replace("%target%", yoTarget.getDisplayName())));

            target.sendMessage(Utils.translate(plugin.getConfig().getString("Speed.TargetMessage")
                    .replace("%speed%", args[0])));
        }

        return true;
    }
}
