package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {

    private final FastCore plugin;

    public InvseeCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Invsee.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.invsee")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Invsee.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Invsee.IncorrectUsage")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Invsee.InvalidPlayer")));
            return true;
        }

        ((Player) sender).openInventory(target.getInventory());

        return true;
    }
}
