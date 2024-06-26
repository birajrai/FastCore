package me.birajrai.core.commands.economy.staff;

import me.birajrai.core.FastCore;
import me.birajrai.core.management.EconomyManagement;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.server.Server;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UnbountyCommand implements CommandExecutor {

    private final FastCore plugin;

    private final EconomyManagement economyManagement = new EconomyManagement();

    public UnbountyCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Unbounty.MustBePlayer")));
            return true;
        }

        Server server = Server.getServer((Player) sender);

        if (!economyManagement.economyIsEnabled(server)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Economy.NotEnabledMessage")));
            return true;
        }

        if (!sender.hasPermission("fcore.unbounty")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Unbounty.NoPermission")));
            return true;
        }

        if (!economyManagement.bountyIsEnabled(server)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.NotEnabledMessage")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Unbounty.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!economyManagement.isInitialized(target)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Unbounty.InvalidPlayer")));
            return true;
        }

        if (!economyManagement.isBountied(server, target)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Unbounty.PlayerNotBountied")));
            return true;
        }

        OfflinePlayer executor = Bukkit.getOfflinePlayer(UUID.fromString(economyManagement.getBountyExecutor(server, target)));

        economyManagement.addMoney(server, executor, economyManagement.getBountyAmount(server, target));
        economyManagement.removeBounty(server, target);

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("Unbounty.Format")
                .replace("%target%", yoTarget.getDisplayName())));

        return true;
    }
}