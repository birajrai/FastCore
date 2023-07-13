package me.birajrai.core.nametags;

import me.birajrai.core.FastCore;
import me.birajrai.core.player.yoPlayer;
import me.birajrai.core.ranks.Rank;
import me.birajrai.core.utils.Utils;
import org.bukkit.entity.Player;

public class TabSetter {

    private final FastCore plugin;

    public TabSetter() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    public void setTabName(Player player) {
        yoPlayer yoPlayer = new yoPlayer(player);

        Rank rank = yoPlayer.getRank();
        if (yoPlayer.isRankDisguised())
            rank = yoPlayer.getRankDisguise();

        String displayName = rank.getColor() + player.getName();
        if (yoPlayer.isNicked())
            displayName = rank.getColor() + yoPlayer.getDisplayNickname();

        if (plugin.modmode_players.contains(player.getUniqueId())) displayName = "&7[M] " + player.getName();
        if (plugin.vanished_players.contains(player.getUniqueId())) displayName = "&7[V] " + player.getName();

        player.setPlayerListName(Utils.translate(displayName));
    }
}
