package me.birajrai.core.commands.economy;

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

import java.text.DecimalFormat;

public class BountyCommand implements CommandExecutor {

    private final FastCore plugin;

    private final EconomyManagement economyManagement = new EconomyManagement();

    public BountyCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.MustBePlayer")));
            return true;
        }

        Server server = Server.getServer((Player) sender);

        if (!economyManagement.economyIsEnabled(server)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Economy.NotEnabledMessage")));
            return true;
        }

        if (!economyManagement.bountyIsEnabled(server)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.NotEnabledMessage")));
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.IncorrectUsage")));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        yoPlayer yoTarget = new yoPlayer(target);

        if (!economyManagement.isInitialized(target)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.InvalidPlayer")));
            return true;
        }

        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");

        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.InvalidAmount")
                    .replace("%minimum%", df.format(plugin.getConfig().getDouble("Bounty.MinimumAmount")))
                    .replace("%maximum%", df.format(plugin.getConfig().getDouble("Economy.MaximumAmount")))));
            return true;
        }

        if (!economyManagement.hasEnoughMoney(server, (Player) sender, Double.parseDouble(args[1]))) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.NotEnoughMoney")));
            return true;
        }

        if (economyManagement.isUnderBountyMinimum(Double.parseDouble(args[1])) || economyManagement.isOverMaximum(Double.parseDouble(args[1]))) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.InvalidAmount")
                    .replace("%minimum%", df.format(plugin.getConfig().getDouble("Bounty.MinimumAmount")))
                    .replace("%maximum%", df.format(plugin.getConfig().getDouble("Economy.MaximumAmount")))));
            return true;
        }

        if (!economyManagement.isBountied(server, target)) {
            economyManagement.setBounty(server, (Player) sender, target, Double.parseDouble(args[1]));

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.Format")
                    .replace("%target%", yoTarget.getDisplayName())
                    .replace("%amount%", df.format(Double.parseDouble(args[1])))));

            for (Player players : Bukkit.getWorld(((Player) sender).getWorld().getName()).getPlayers())
                players.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.Broadcast")
                        .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())
                        .replace("%target%", yoTarget.getDisplayName())
                        .replace("%amount%", df.format(Double.parseDouble(args[1])))));
        } else {
            economyManagement.increaseBounty(server, (Player) sender, target, Double.parseDouble(args[1]));

            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.FormatIncreased")
                    .replace("%target%", yoTarget.getDisplayName())
                    .replace("%amount%", df.format(economyManagement.getBountyAmount(server, target)))));

            for (Player players : Bukkit.getWorld(((Player) sender).getWorld().getName()).getPlayers())
                players.sendMessage(Utils.translate(plugin.getConfig().getString("Bounty.BroadcastIncreased")
                        .replace("%player%", yoPlayer.getYoPlayer((Player) sender).getDisplayName())
                        .replace("%target%", yoTarget.getDisplayName())
                        .replace("%amount%", df.format(economyManagement.getBountyAmount(server, target)))));
        }

        return true;
    }
}