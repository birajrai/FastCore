package me.birajrai.core.gui.guis;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.Button;
import me.birajrai.core.gui.CustomGUI;
import me.birajrai.core.gui.GUI;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.ItemBuilder;
import me.birajrai.core.utils.Utils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GrantReasonGUI extends CustomGUI {

    private final FastCore plugin;

    public GrantReasonGUI(Player player, int size, String title) {
        super(player, size, title);

        plugin = FastCore.getPlugin(FastCore.class);
    }

    public void setup(Player player, OfflinePlayer target) {
        yoPlayer yoTarget = new yoPlayer(target);

        for (String cItem : plugin.getConfig().getConfigurationSection("Grant.Reason.Items").getKeys(false)) {
            List<String> itemLore = new ArrayList<>();
            for (String line : plugin.getConfig().getStringList("Grant.Reason.Lore")) {
                itemLore.add(line
                        .replace("%reason%", plugin.getConfig().getString("Grant.Reason.Items." + cItem + ".Name"))
                        .replace("%target%", yoTarget.getDisplayName()));
            }

            ItemBuilder itemBuilder = new ItemBuilder(
                    Utils.getMaterialFromConfig(plugin.getConfig().getString("Grant.Reason.Items." + cItem + ".Item")),
                    1,
                    plugin.getConfig().getString("Grant.Reason.Items." + cItem + ".Name"),
                    ItemBuilder.translateLore(itemLore)
            );

            gui.setButton(plugin.getConfig().getInt("Grant.Reason.Items." + cItem + ".Slot"), new Button(
                    itemBuilder.getItem(),
                    () -> {
                        GUI.close(gui);

                        if (!plugin.getConfig().getString("Grant.Reason.Items." + cItem + ".ID").equalsIgnoreCase(Utils.translate(plugin.getConfig().getString("Grant.Reason.Items.Custom.ID")))) {
                            plugin.grant_reason.put(player.getUniqueId(), plugin.getConfig().getString("Grant.Reason.Items." + cItem + ".ID"));

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    GrantConfirmGUI grantConfirmGUI = new GrantConfirmGUI(player, 45, "&aConfirm the grant.");
                                    grantConfirmGUI.setup(player, target, plugin.grant_grant.get(player.getUniqueId()), plugin.grant_duration.get(player.getUniqueId()), plugin.grant_reason.get(player.getUniqueId()));
                                    GUI.open(grantConfirmGUI.getGui());
                                }
                            }.runTaskLater(plugin, 1);

                        } else {
                            plugin.grant_custom_reason.add(player.getUniqueId());
                            player.sendMessage(Utils.translate(plugin.getConfig().getString("Grant.Reason.CustomReasonChatMessage")));
                        }
                    },
                    itemBuilder.getName(),
                    itemBuilder.getLore()
            ));
        }
    }
}
