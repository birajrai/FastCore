package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleStaffAlertsCommand implements CommandExecutor {

    private final FastCore plugin;

    public ToggleStaffAlertsCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.staffalerts")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.NoPermission")));
            return true;
        }

        if (!plugin.staff_alerts.contains(((Player) sender).getUniqueId())) {
            plugin.staff_alerts.add(((Player) sender).getUniqueId());
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.ToggleOnMessage")));
        } else {
            plugin.staff_alerts.remove(((Player) sender).getUniqueId());
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("StaffAlerts.ToggleOffMessage")));
        }

        return true;
    }
}
