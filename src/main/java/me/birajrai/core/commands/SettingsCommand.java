package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.GUI;
import me.birajrai.core.gui.guis.SettingsGUI;
import me.birajrai.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {

    private final FastCore plugin;

    public SettingsCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("Settings.MustBePlayer")));
            return true;
        }

        SettingsGUI settingsGUI = new SettingsGUI((Player) sender, 9, "&aPlayer settings.");
        settingsGUI.setup();
        GUI.open(settingsGUI.getGui());

        return true;
    }
}
