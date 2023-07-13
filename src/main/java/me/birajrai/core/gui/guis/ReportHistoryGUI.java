package me.birajrai.core.gui.guis;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.*;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.utils.ItemBuilder;
import me.birajrai.core.utils.Utils;
import me.birajrai.core.utils.XMaterial;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ReportHistoryGUI extends CustomGUI implements PagedGUI {

    private final FastCore plugin;

    public ReportHistoryGUI(Player player, int size, String title) {
        super(player, size, title);
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public void setupPagedGUI(Map<Integer, Button> entry, int page) {
        for (Map.Entry<Integer, Button> button : entry.entrySet()) {
            int[] data = Utils.getHistorySlotData(button.getKey());
            if (page == data[0])
                gui.setButton(data[1] + 9, button.getValue());
        }
    }

    public void setup(OfflinePlayer target, int page) {
        yoPlayer yoTarget = new yoPlayer(target);

        Map<Integer, Button> buttons = new HashMap<>();
        Set<Integer> pages = new HashSet<>();

        if (plugin.playerData.config.contains(target.getUniqueId().toString() + ".Report")) {
            int loop = -1;
            for (String report : plugin.playerData.config.getConfigurationSection(target.getUniqueId().toString() + ".Report").getKeys(false)) {
                loop++;
                ItemBuilder itemBuilder = new ItemBuilder(
                        XMaterial.LIME_WOOL.parseItem(),
                        1,
                        "&a&l(Active) " + Utils.getExpirationDate(plugin.playerData.config.getLong(target.getUniqueId().toString() + ".Report." + report + ".Date")),
                        ItemBuilder.formatLore(new String[]{
                                "&3&m----------------------------",
                                "&bTarget: &3" + yoTarget.getDisplayName(),
                                "&bIssued By: &3" + yoPlayer.getYoPlayer(UUID.fromString(plugin.playerData.config.getString(target.getUniqueId().toString() + ".Report." + report + ".Executor"))).getDisplayName(),
                                "&bIssued Reason: &3" + plugin.playerData.config.getString(target.getUniqueId().toString() + ".Report." + report + ".Reason"),
                                "&3&m----------------------------"
                        })
                );

                buttons.put(loop, new Button(
                        itemBuilder.getItem(),
                        itemBuilder.getName(),
                        itemBuilder.getLore()
                ));
            }

            for (Map.Entry<Integer, Button> entry : buttons.entrySet()) pages.add((entry.getKey() / 9) + 1);

            Toolbar toolbar = new Toolbar(getGui(), "Reports", page, new ArrayList<>(pages), () -> new BukkitRunnable() {
                @Override
                public void run() {
                    ReportHistoryGUI reportHistoryGUI = new ReportHistoryGUI(player, 18, yoTarget.getDisplayName() + "&a's report history.");
                    reportHistoryGUI.setup(target, Toolbar.getNewPage().get());
                    GUI.open(reportHistoryGUI.getGui());
                }
            }.runTaskLater(plugin, 1));

            toolbar.create(target, null, false);
            setupPagedGUI(buttons, page);
        }
    }
}
