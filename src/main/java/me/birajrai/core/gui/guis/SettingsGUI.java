package me.birajrai.core.gui.guis;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.Button;
import me.birajrai.core.gui.CustomGUI;
import me.birajrai.core.gui.GUI;
import me.birajrai.core.utils.ItemBuilder;
import me.birajrai.core.utils.Utils;
import me.birajrai.core.utils.XMaterial;
import org.bukkit.entity.Player;

public class SettingsGUI extends CustomGUI {

    private final FastCore plugin;

    public SettingsGUI(Player player, int size, String title) {
        super(player, size, title);
        plugin = FastCore.getPlugin(FastCore.class);
    }

    public void setup() {
        String tpmStatus;
        if (plugin.message_toggled.contains(gui.getPlayer().getUniqueId())) tpmStatus = "&cOff";
        else tpmStatus = "&aOn";

        String soundsStatus;
        if (plugin.message_sounds_toggled.contains(gui.getPlayer().getUniqueId())) soundsStatus = "&cOff";
        else soundsStatus = "&aOn";

        String chatStatus;
        if (plugin.chat_toggled.contains(gui.getPlayer().getUniqueId())) chatStatus = "&cOff";
        else chatStatus = "&aOn";

        String[] lore = new String[]{
                "&7&m-------------------",
                "&aClick to select.",
                "&7&m-------------------"
        };

        ItemBuilder tpm = new ItemBuilder(XMaterial.WRITABLE_BOOK.parseItem(), 1, "&bPrivate Messages: &3" + tpmStatus, ItemBuilder.formatLore(lore));
        ItemBuilder sounds = new ItemBuilder(XMaterial.CLOCK.parseItem(), 1, "&bMessage Sounds: &3" + soundsStatus, ItemBuilder.formatLore(lore));
        ItemBuilder chat = new ItemBuilder(XMaterial.PAPER.parseItem(), 1, "&bGlobal Chat: &3" + chatStatus, ItemBuilder.formatLore(lore));
        ItemBuilder scoreboard = new ItemBuilder(XMaterial.PAINTING.parseItem(), 1, "&bToggle Scoreboard", ItemBuilder.formatLore(lore));

        gui.setButton(0, new Button(
                tpm.getItem(),
                () -> {
                    GUI.close(gui);
                    gui.getPlayer().performCommand("tpm");
                },
                tpm.getName(),
                tpm.getLore()
        ));
        gui.setButton(1, new Button(
                sounds.getItem(),
                () -> {
                    GUI.close(gui);

                    if (!plugin.message_sounds_toggled.contains(gui.getPlayer().getUniqueId())) {
                        gui.getPlayer().sendMessage(Utils.translate(plugin.getConfig().getString("Settings.MessageSoundsOff")));
                        plugin.message_sounds_toggled.add(gui.getPlayer().getUniqueId());
                    } else {
                        gui.getPlayer().sendMessage(Utils.translate(plugin.getConfig().getString("Settings.MessageSoundsOn")));
                        plugin.message_sounds_toggled.remove(gui.getPlayer().getUniqueId());
                    }
                },
                sounds.getName(),
                sounds.getLore()
        ));
        gui.setButton(2, new Button(
                chat.getItem(),
                () -> {
                    GUI.close(gui);

                    if (!plugin.chat_toggled.contains(gui.getPlayer().getUniqueId())) {
                        gui.getPlayer().sendMessage(Utils.translate(plugin.getConfig().getString("Settings.GlobalChatOff")));
                        plugin.chat_toggled.add(gui.getPlayer().getUniqueId());
                    } else {
                        gui.getPlayer().sendMessage(Utils.translate(plugin.getConfig().getString("Settings.GlobalChatOn")));
                        plugin.chat_toggled.remove(gui.getPlayer().getUniqueId());
                    }
                },
                chat.getName(),
                chat.getLore()
        ));
        gui.setButton(8, new Button(
                scoreboard.getItem(),
                () -> {
                    GUI.close(gui);
                    gui.getPlayer().performCommand("tsb");
                },
                scoreboard.getName(),
                scoreboard.getLore()
        ));
    }
}
