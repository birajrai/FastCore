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

public class GrantDurationGUI extends CustomGUI {

    private final FastCore plugin;

    public GrantDurationGUI(Player player, int size, String title) {
        super(player, size, title);

        plugin = FastCore.getPlugin(FastCore.class);
    }

    public void setup(Player player, OfflinePlayer target) {
        yoPlayer yoTarget = new yoPlayer(target);

        for (String cItem : plugin.getConfig().getConfigurationSection("Grant.Duration.Items").getKeys(false)) {
            List<String> itemLore = new ArrayList<>();
            for (String line : plugin.getConfig().getStringList("Grant.Duration.Lore")) {
                itemLore.add(line
                        .replace("%duration%", plugin.getConfig().getString("Grant.Duration.Items." + cItem + ".Name"))
                        .replace("%target%", yoTarget.getDisplayName()));
            }

            ItemBuilder itemBuilder = new ItemBuilder(
                    Utils.getMaterialFromConfig(plugin.getConfig().getString("Grant.Duration.Items." + cItem + ".Item")),
                    1,
                    plugin.getConfig().getString("Grant.Duration.Items." + cItem + ".Name"),
                    ItemBuilder.translateLore(itemLore)
            );

            gui.setButton(plugin.getConfig().getInt("Grant.Duration.Items." + cItem + ".Slot"), new Button(
                    itemBuilder.getItem(),
                    () -> {
                        GUI.close(gui);

                        plugin.grant_duration.put(player.getUniqueId(), plugin.getConfig().getString("Grant.Duration.Items." + cItem + ".ID"));

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                GrantReasonGUI grantReasonGUI = new GrantReasonGUI(player, 27, "&aSelect a reason.");
                                grantReasonGUI.setup(player, target);
                                GUI.open(grantReasonGUI.getGui());
                            }
                        }.runTaskLater(plugin, 1);
                    },
                    itemBuilder.getName(),
                    itemBuilder.getLore()
            ));
        }
    }
}
