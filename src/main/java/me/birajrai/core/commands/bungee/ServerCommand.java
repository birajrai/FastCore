package me.birajrai.core.commands.bungee;

import me.birajrai.core.FastCore;
import me.birajrai.core.management.PlayerManagement;
import me.birajrai.core.server.Server;
import me.birajrai.core.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ServerCommand implements CommandExecutor {

    private final FastCore plugin;
    private final PlayerManagement playerManagement = new PlayerManagement();

    public ServerCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ServerCommand.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.server")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ServerCommand.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ServerCommand.IncorrectUsage")));
            return true;
        }

        Server server = Server.getServer(args[0]);

        if (server == null || !Server.getServers().containsKey(server.getName())) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ServerCommand.InvalidServer")));
            return true;
        }

        if (plugin.modmode_players.contains(((Player) sender).getUniqueId())) {
            ((Player) sender).getInventory().clear();

            ((Player) sender).getInventory().setContents(plugin.inventory_contents.get(((Player) sender).getUniqueId()));
            ((Player) sender).getInventory().setArmorContents(plugin.armor_contents.get(((Player) sender).getUniqueId()));

            ((Player) sender).updateInventory();

            ((Player) sender).setAllowFlight(false);
            ((Player) sender).setFlying(false);

            plugin.modmode_players.remove(((Player) sender).getUniqueId());
        }

        if (plugin.last_location.get(((Player) sender).getUniqueId()) == null) {
            Map<Server, Location> location = new HashMap<>();
            location.put(Server.getServer((Player) sender), ((Player) sender).getLocation());
            plugin.last_location.put(((Player) sender).getUniqueId(), location);
        }

        plugin.last_location.get(((Player) sender).getUniqueId()).put(Server.getServer((Player) sender), ((Player) sender).getLocation());

        if (plugin.getConfig().getBoolean("Spawn.SpawnOnServerChange"))
            playerManagement.sendToSpawn(server, (Player) sender);
        else {
            if (plugin.last_location.get(((Player) sender).getUniqueId()).containsKey(server))
                ((Player) sender).teleport(plugin.last_location.get(((Player) sender).getUniqueId()).get(server));
            else playerManagement.sendToSpawn(server, (Player) sender);
        }

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("ServerCommand.Format")
                .replace("%server%", server.getName())));

        return true;
    }
}
