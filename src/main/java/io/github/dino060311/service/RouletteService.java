package io.github.dino060311.service;

import io.github.dino060311.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RouletteService {
    private final Main plugin;
    public RouletteService(Main plugin) {
        this.plugin = plugin;
    }

    public void startRoulette(Player player, String finalJob) {
        Inventory inv = org.bukkit.Bukkit.createInventory(null, 9, "§0직업 결정 중...");
        player.openInventory(inv);

        Material[] displayItems = { Material.CROSSBOW, Material.BREAD, Material.DIAMOND_SWORD, Material.TOTEM_OF_UNDYING };

        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if (count >= 20) { // 20번 돌아가면 멈춤
                    stopRoulette(player, finalJob);
                    this.cancel();
                    return;
                }

                // 아이템 셔플 애니메이션
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.5f, 2.0f);
                inv.setItem(4, new ItemStack(displayItems[count % 4]));
                count++;
            }
        }.runTaskTimer(plugin, 0, 3); // 3틱마다 실행 (0.15초)
    }

    private void stopRoulette(Player player, String job) {
        Inventory inv = player.getOpenInventory().getTopInventory();
        Material finalMat = Material.BREAD;

        if (job.equals("마피아")) finalMat = Material.CROSSBOW;
        else if (job.equals("경찰")) finalMat = Material.DIAMOND_SWORD;
        else if (job.equals("의사")) finalMat = Material.TOTEM_OF_UNDYING;

        inv.setItem(4, new ItemStack(finalMat));
        player.sendMessage("§f[!] 당신의 직업은... §e§l" + job + " §f입니다!");
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);

        if (job.equals("마피아")) {
            player.getInventory().addItem(new ItemStack(Material.CROSSBOW));
            player.getInventory().addItem(new ItemStack(Material.ARROW, 10)); // 화살 10개
        } else if (job.equals("경찰")) {
            player.getInventory().addItem(new ItemStack(Material.IRON_SWORD)); // 철검
        } else if (job.equals("의사")) {
            player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING)); // 불사의 토템
        } else { // 시민
            player.getInventory().addItem(new ItemStack(Material.BREAD, 10)); // 식량 10개

        }

        // 메시지와 함께 창 닫기
        new BukkitRunnable() {
            @Override
            public void run() {
                player.closeInventory();
                player.sendMessage("§a[System] 게임이 곧 시작됩니다. 준비하세요!");
            }
        }.runTaskLater(plugin, 30);

    }
}