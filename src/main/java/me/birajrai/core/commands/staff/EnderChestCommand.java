package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {

    private final FastCore plugin;

    public EnderChestCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("EnderChest.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.echest")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("EnderChest.NoPermission")));
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("EnderChest.IncorrectUsage")));
            return true;
        }

        if (args.length == 0) {
            ((Player) sender).openInventory(((Player) sender).getEnderChest());
        } else {
            if (!sender.hasPermission("fcore.echest.others")) {
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("EnderChest.NoPermission")));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("EnderChest.InvalidPlayer")));
                return true;
            }

            ((Player) sender).openInventory(target.getEnderChest());
        }

        return true;
    }
}
