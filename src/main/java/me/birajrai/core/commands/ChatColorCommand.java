package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.GUI;
import me.birajrai.core.gui.guis.ChatColorGUI;
import me.birajrai.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatColorCommand implements CommandExecutor {

    private final FastCore plugin;

    public ChatColorCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ChatColor.MustBePlayer")));
            return true;
        }

        if (!sender.hasPermission("fcore.chatcolor")) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("ChatColor.NoPermission")));
            return true;
        }

        ChatColorGUI chatColorGUI = new ChatColorGUI((Player) sender, 36, "&aSelect a chat color.");
        chatColorGUI.setup();
        GUI.open(chatColorGUI.getGui());

        return true;
    }
}
