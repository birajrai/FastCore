package me.birajrai.core.gui.guis;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.*;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.punishments.Punishment;
import me.birajrai.core.punishments.PunishmentType;
import me.birajrai.core.utils.ItemBuilder;
import me.birajrai.core.utils.Utils;
import me.birajrai.core.utils.XMaterial;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DetailedPunishmentHistoryGUI extends CustomGUI implements PagedGUI {

    private final FastCore plugin;

    public DetailedPunishmentHistoryGUI(Player player, int size, String title) {
        super(player, size, title);
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public void setupPagedGUI(Map<Integer, Button> buttons, int page) {
        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            int[] data = Utils.getHistorySlotData(entry.getKey());
            if (page == data[0])
                gui.setButton(data[1] + 9, entry.getValue());
        }
    }

    public void setup(PunishmentType type, Player player, OfflinePlayer target, int page) {
        yoPlayer yoTarget = new yoPlayer(target);

        String activePrefix = "&a&l(Active) ";
        String revokedPrefix = "&4&l(Revoked) ";
        String expiredPrefix = "&6&l(Expired) ";

        Map<Integer, Button> buttons = new HashMap<>();
        Set<Integer> pages = new HashSet<>();

        if (plugin.punishmentData.config.contains(yoTarget.getPlayer().getUniqueId().toString() + "." + PunishmentType.convertToString(type))) {
            int loop = -1;
            for (Map.Entry<Integer, Punishment> punishment : Punishment.getPunishments(yoTarget).entrySet()) {
                if (punishment.getValue().getType() == type) {
                    loop++;

                    String executor;
                    if (punishment.getValue().getExecutor().equalsIgnoreCase("CONSOLE")) executor = "&c&lConsole";
                    else executor = new yoPlayer(UUID.fromString(punishment.getValue().getExecutor())).getDisplayName();

                    String duration;
                    if (punishment.getValue().getDuration() instanceof String)
                        duration = "Permanent";
                    else
                        duration = Utils.getExpirationDate((long) punishment.getValue().getDuration());
                    String reason = punishment.getValue().getReason();
                    String silent = String.valueOf(punishment.getValue().getSilent());

                    ItemBuilder itemBuilder = new ItemBuilder(XMaterial.BEDROCK.parseItem(), 1, "&4&lNULL", ItemBuilder.formatLore(new String[]{
                            "&3&m----------------------------",
                            "&bTarget: &3" + yoTarget.getDisplayName(),
                            "&bDuration: &3" + duration,
                            "&b ",
                            "&bIssued By: &3" + executor,
                            "&bIssued Reason: &3" + reason,
                            "&bIssued Silently: &3" + silent,
                            "&3&m----------------------------"
                    }));

                    switch (punishment.getValue().getStatus().toLowerCase()) {
                        case "active":
                            itemBuilder.setItem(XMaterial.LIME_WOOL.parseItem());
                            itemBuilder.setName(activePrefix + Utils.getExpirationDate(punishment.getValue().getDate()));

                            if (punishment.getValue().getStatus().equalsIgnoreCase("Active")
                                    && player.hasPermission("fcore.un" + type.toString().toLowerCase())
                                    && type != PunishmentType.WARN)
                                itemBuilder.getLore().add(Utils.translate("&aClick to revoke this punishment."));
                            break;
                        case "revoked":
                            itemBuilder.setItem(XMaterial.RED_WOOL.parseItem());
                            itemBuilder.setName(revokedPrefix + Utils.getExpirationDate(punishment.getValue().getDate()));
                            break;
                        case "expired":
                            itemBuilder.setItem(XMaterial.ORANGE_WOOL.parseItem());
                            itemBuilder.setName(expiredPrefix + Utils.getExpirationDate(punishment.getValue().getDate()));
                            break;
                    }

                    Button button = new Button(
                            itemBuilder.getItem(),
                            () -> {
                                if (type != PunishmentType.WARN && player.hasPermission("fcore.un" + type.toString().toLowerCase())
                                        && itemBuilder.getLore().contains(Utils.translate("&aClick to revoke this punishment."))) {
                                    GUI.close(gui);
                                    player.performCommand("un" + type.toString().toLowerCase() + " " + target.getName() + " -s");
                                }
                            },
                            itemBuilder.getName(),
                            itemBuilder.getLore()
                    );

                    buttons.put(loop, button);
                }
            }

            for (Map.Entry<Integer, Button> entry : buttons.entrySet()) pages.add((entry.getKey() / 9) + 1);

            Toolbar toolbar = new Toolbar(getGui(), "Punishments", page, new ArrayList<>(pages), () -> new BukkitRunnable() {
                @Override
                public void run() {
                    DetailedPunishmentHistoryGUI detailedPunishmentHistoryGUI = new DetailedPunishmentHistoryGUI(player, 27, yoTarget.getDisplayName() + "&a's " + type.toString().toLowerCase() + "&as.");
                    detailedPunishmentHistoryGUI.setup(type, player, target, Toolbar.getNewPage().get());
                    GUI.open(detailedPunishmentHistoryGUI.getGui());
                }
            }.runTaskLater(plugin, 1));

            toolbar.create(target, type, true);
            setupPagedGUI(buttons, page);
        }
    }
}
