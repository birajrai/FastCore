package me.birajrai.core.commands.bungee;

import me.birajrai.core.FastCore;
import me.birajrai.core.management.PlayerManagement;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.server.Server;
import me.birajrai.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SendCommand implements CommandExecutor {

    private final FastCore plugin;
    private final PlayerManagement playerManagement = new PlayerManagement();

    public SendCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fcore.send")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Send.NoPermission")));
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Send.IncorrectUsage")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Send.InvalidPlayer")));
            return true;
        }

        Server server = Server.getServer(args[1]);

        if (server == null || !Server.getServers().containsKey(server.getName())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Send.InvalidServer")));
            return true;
        }

        if (plugin.modmode_players.contains(target.getUniqueId())) {
            target.getInventory().clear();

            target.getInventory().setContents(plugin.inventory_contents.get(target.getUniqueId()));
            target.getInventory().setArmorContents(plugin.armor_contents.get(target.getUniqueId()));

            target.updateInventory();

            target.setAllowFlight(false);
            target.setFlying(false);

            plugin.modmode_players.remove(target.getUniqueId());
        }

        if (plugin.last_location.get(target.getUniqueId()) == null) {
            Map<Server, Location> location = new HashMap<>();
            location.put(Server.getServer(target), target.getLocation());
            plugin.last_location.put(target.getUniqueId(), location);
        }

        plugin.last_location.get(target.getUniqueId()).put(Server.getServer(target), target.getLocation());

        if (plugin.getConfig().getBoolean("Spawn.SpawnOnServerChange"))
            playerManagement.sendToSpawn(server, target);
        else {
            if (plugin.last_location.get(target.getUniqueId()).containsKey(server))
                target.teleport(plugin.last_location.get(target.getUniqueId()).get(server));
            else playerManagement.sendToSpawn(server, target);
        }

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("Send.ExecutorMessage")
                .replace("%target%", yoPlayer.getYoPlayer(target).getDisplayName())
                .replace("%server%", server.getName())));

        target.sendMessage(Utils.translate(plugin.getConfig().getString("Send.TargetMessage")
                .replace("%server%", server.getName())));

        return true;
    }
}
