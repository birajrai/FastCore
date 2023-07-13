package me.birajrai.core.commands.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final FastCore plugin;

    public ReloadCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.reload")) {
            return true;
        }

        plugin.reloadConfig();
        plugin.registerData();

        sender.sendMessage(Utils.translate("&aReloaded all files."));

        return true;
    }
}
