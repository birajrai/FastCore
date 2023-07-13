package me.birajrai.core.gui.guis;

import me.birajrai.core.FastCore;
import me.birajrai.core.gui.*;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.ranks.Rank;
import me.birajrai.core.server.Server;
import me.birajrai.core.utils.ItemBuilder;
import me.birajrai.core.utils.Utils;
import me.birajrai.core.utils.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class OnlinePlayersGUI extends CustomGUI implements PagedGUI {

    private final FastCore plugin;

    public OnlinePlayersGUI(Player player, int size, String title) {
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

    public void setup(int page) {
        Map<Integer, Button> buttons = new HashMap<>();
        Set<Integer> pages = new HashSet<>();

        int loop = -1;
        for (Player players : Server.getPlayers(Server.getServer(gui.getPlayer()))) {
            yoPlayer yoPlayer = new yoPlayer(players);

            loop++;
            ItemStack skull = XMaterial.PLAYER_HEAD.parseItem();
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwner(players.getName());
            skull.setItemMeta(skullMeta);

            ItemStack item = skull;
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Utils.translate(yoPlayer.getDisplayName()));

            Rank rank = yoPlayer.getRank();

            String vanish = String.valueOf(plugin.vanished_players.contains(players.getUniqueId()));
            String modmode = String.valueOf(plugin.modmode_players.contains(players.getUniqueId()));

            String[] lore = new String[]{
                    "&3&m--------------------------",
                    "&bRank: &3" + rank.getDisplay(),
                    "&bVanish: &3" + vanish,
                    "&bModMode: &3" + modmode,
                    "&7 ",
                    "&aClick to teleport to " + yoPlayer.getDisplayName() + "&a.",
                    "&3&m--------------------------"
            };

            itemMeta.setLore(ItemBuilder.formatLore(lore));
            item.setItemMeta(itemMeta);

            Button button = new Button(
                    item,
                    () -> {
                        GUI.close(gui);
                        gui.getPlayer().performCommand("tp " + players.getName());
                    },
                    item.getItemMeta().getDisplayName(),
                    item.getItemMeta().getLore());

            buttons.put(loop, button);
        }

        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) pages.add((entry.getKey() / 9) + 1);

        Toolbar toolbar = new Toolbar(getGui(), "OnlinePlayers", page, new ArrayList<>(pages), () -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    OnlinePlayersGUI onlinePlayersGUI = new OnlinePlayersGUI(player, 18, "&aOnline players.");
                    onlinePlayersGUI.setup(Toolbar.getNewPage().get());
                    GUI.open(onlinePlayersGUI.getGui());
                }
            }.runTaskLater(plugin, 1);
        });
        toolbar.create(null, null, false);
        setupPagedGUI(buttons, page);
    }
}
