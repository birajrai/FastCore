package me.birajrai.core.commands;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.GUI;
import me.birajrai.core.gui.guis.TagsGUI;
import me.birajrai.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagsCommand implements CommandExecutor {

    private final FastCore plugin;

    public TagsCommand() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("TagsCommand.MustBePlayer")));
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(Utils.translate(plugin.getConfig().getString("TagsCommand.IncorrectUsage")));
            return true;
        }

        if (args.length == 0) {
            TagsGUI tagsGUI = new TagsGUI((Player) sender, 18, "&aChat tags.");
            tagsGUI.setup(1);
            GUI.open(tagsGUI.getGui());
        } else {
            if (args[0].equalsIgnoreCase("off")) {
                plugin.tag.remove(((Player) sender).getUniqueId());
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("TagsCommand.FormatOff")));
            } else {
                sender.sendMessage(Utils.translate(plugin.getConfig().getString("TagsCommand.IncorrectUsage")));
                return true;
            }
        }

        return true;
    }
}
