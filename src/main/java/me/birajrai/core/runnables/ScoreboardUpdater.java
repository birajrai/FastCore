package me.birajrai.core.runnables;

import me.birajrai.core.FastCore;
import me.birajrai.core.scoreboard.ScoreboardSetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardUpdater extends BukkitRunnable {

    private final FastCore plugin;
    private final ScoreboardSetter scoreboardSetter = new ScoreboardSetter();

    public ScoreboardUpdater() {
        plugin = FastCore.getPlugin(FastCore.class);
    }

    @Override
    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!plugin.tsb.contains(players.getUniqueId()))
                scoreboardSetter.scoreboard(players);
        }
    }
}