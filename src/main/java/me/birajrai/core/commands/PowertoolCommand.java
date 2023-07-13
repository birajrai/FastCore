package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import me.birajrai.core.utils.XMaterial;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PowertoolCommand implements CommandExecutor {

    private final FastCore plugin;

    public PowertoolCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Powertool.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.powertool")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Powertool.NoPermission")));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Powertool.IncorrectUsage")));
            return true;
        }

        if (((Player) sender).getInventory().getItemInHand().getType() == XMaterial.AIR.parseMaterial()) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Powertool.InvalidItem")));
            return true;
        }

        if (!args[0].equalsIgnoreCase("off")) {
            String cmd = "";
            for (int i = 0; i < args.length; i++) {
                if (cmd.equalsIgnoreCase("")) cmd = args[i];
                else cmd = cmd + " " + args[i];
            }

            plugin.powertool_command.remove(((Player) sender).getUniqueId());
            plugin.powertool_material.remove(((Player) sender).getUniqueId());

            plugin.powertool_command.put(((Player) sender).getUniqueId(), cmd);
            plugin.powertool_material.put(((Player) sender).getUniqueId(), ((Player) sender).getInventory().getItemInHand().getType());

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Powertool.FormatOn")
                    .replace("%command%", cmd)
                    .replace("%item%", ((Player) sender).getInventory().getItemInHand().getType().toString())));
        } else {
            plugin.powertool_command.remove(((Player) sender).getUniqueId());
            plugin.powertool_material.remove(((Player) sender).getUniqueId());

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Powertool.FormatOff")));
        }

        return true;
    }
}
