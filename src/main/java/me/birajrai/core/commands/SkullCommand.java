package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.utils.Utils;
import me.birajrai.core.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand implements CommandExecutor {

    private final FastCore plugin;

    public SkullCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Skull.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.skull")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Skull.NoPermission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Skull.IncorrectUsage")));
            return true;
        }

        ItemStack skull = XMaterial.PLAYER_HEAD.parseItem();
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(args[0]));
        skull.setItemMeta(skullMeta);

        ((Player) sender).getInventory().addItem(skull);
        ((Player) sender).updateInventory();

        sender.sendMessage(Utils.translate(plugin.getConfig().getString("Skull.Format")
                .replace("%target%", Bukkit.getOfflinePlayer(args[0]).getName())));

        return true;
    }
}
