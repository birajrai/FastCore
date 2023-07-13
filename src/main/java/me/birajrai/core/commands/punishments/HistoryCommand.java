package me.birajrai.core.commands.punishments;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.GUI;
import me.birajrai.core.gui.guis.PunishmentHistoryGUI;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HistoryCommand implements CommandExecutor {

    private final FastCore plugin;

    public HistoryCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("History.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.history")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("History.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("History.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!plugin.playerData.config.contains(target.getUniqueId().toString())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("History.InvalidPlayer")));
            return true;
        }

        PunishmentHistoryGUI punishmentHistoryGUI = new PunishmentHistoryGUI((Player) sender, 36, "&aSelect Punishment Type.");
        punishmentHistoryGUI.setup((Player) sender, target);
        GUI.open(punishmentHistoryGUI.getGui());

        plugin.selected_history.put(((Player) sender).getUniqueId(), target.getUniqueId());

        return true;
    }
}
