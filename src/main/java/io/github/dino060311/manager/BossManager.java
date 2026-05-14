package io.github.dino060311.manager;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossManager {
    private static BossBar bossBar;

    public static void createBossBar(String title) {
        if (bossBar != null) bossBar.removeAll();
        bossBar = Bukkit.createBossBar(title, BarColor.RED, BarStyle.SEGMENTED_10);
        for (Player p : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(p);
        }
    }

    public static void updateProgress(double progress) {
        if (bossBar != null) {
            bossBar.setProgress(Math.max(0.0, Math.min(1.0, progress)));
        }
    }

    public static void updatePhase(String newTitle, BarColor color) {
        if (bossBar != null) {
            bossBar.setTitle(newTitle);
            bossBar.setColor(color);
        }
    }

    public static void removeBossBar() {
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar = null;
        }
    }
}