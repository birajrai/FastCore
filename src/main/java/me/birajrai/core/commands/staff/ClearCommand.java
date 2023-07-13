package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.Utils;
import me.birajrai.core.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearCommand implements CommandExecutor {

    private final FastCore plugin;

    public ClearCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Clear.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.clear")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Clear.NoPermission")));
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Clear.IncorrectUsage")));
            return true;
        }

        if (args.length == 0) {
            ((Player) sender).getInventory().clear();
            ((Player) sender).getInventory().setHelmet(XMaterial.AIR.parseItem());
            ((Player) sender).getInventory().setChestplate(XMaterial.AIR.parseItem());
            ((Player) sender).getInventory().setLeggings(XMaterial.AIR.parseItem());
            ((Player) sender).getInventory().setBoots(XMaterial.AIR.parseItem());
            ((Player) sender).updateInventory();

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Clear.TargetMessage")));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            yoPlayer yoTarget = new yoPlayer(target);

            if (target == null) {
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("Clear.InvalidPlayer")));
                return true;
            }

            target.getInventory().clear();
            target.getInventory().setHelmet(XMaterial.AIR.parseItem());
            target.getInventory().setChestplate(XMaterial.AIR.parseItem());
            target.getInventory().setLeggings(XMaterial.AIR.parseItem());
            target.getInventory().setBoots(XMaterial.AIR.parseItem());
            target.updateInventory();

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Clear.ExecutorMessage")
                    .replace("%target%", yoTarget.getDisplayName())));
            target.sendMessage(Utils.translate(plugin.getConfig().getString("Clear.TargetMessage")));
        }

        return true;
    }
}
