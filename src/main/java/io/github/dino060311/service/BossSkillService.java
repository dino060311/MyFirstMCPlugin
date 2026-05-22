package io.github.dino060311.service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import io.github.dino060311.Main;
import io.github.dino060311.manager.BossManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class BossSkillService {
    private final Main plugin;

    public BossSkillService(Main plugin) {
        this.plugin = plugin;
    }

    // 2페이즈: 광역 밀치기 (차징형)
    public void areaPush(Location bossLoc) {

        // 차징 연출
        bossLoc.getWorld().playSound(bossLoc, Sound.BLOCK_BEACON_ACTIVATE, 2.0f, 0.7f);
        bossLoc.getWorld().spawnParticle(Particle.ENCHANT, bossLoc, 120, 1.5, 1.5, 1.5, 0.1);

        // 주변 플레이어 경고
        bossLoc.getWorld().getNearbyPlayers(bossLoc, 15).forEach(player -> {
            Component bossMsg = Component.text("[보스 좀비] ", NamedTextColor.DARK_RED)
                    .append(Component.text("저리 비켜라!", NamedTextColor.WHITE, TextDecoration.BOLD));
            Component warningMsg = Component.text("⚠ ", NamedTextColor.GOLD)
                    .append(Component.text("보스가 힘을 모으고 있습니다! 피하세요!", NamedTextColor.RED));

            player.sendMessage(bossMsg);
            player.sendMessage(warningMsg);
        });

        // 2초 뒤 폭발
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            
            // 보스가 이미 죽었으면 스킬 취소
            if (!BossManager.hasBoss()) {
                return;
            }

            bossLoc.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, bossLoc, 5);
            bossLoc.getWorld().playSound(bossLoc, Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 0.8f);
            bossLoc.getWorld().getNearbyEntities(bossLoc, 5.0, 5.0, 5.0).forEach(entity -> {

                if (entity instanceof Player p) {
                    p.setVelocity(p.getLocation().toVector().subtract(bossLoc.toVector()).normalize().multiply(2.5));
                }
            });
        }, 40L); // 2초 차징
    }

    // 최종페이즈: 번개 심판
    public void lastResort(Location bossLoc) {

        // 궁극기 예고
        bossLoc.getWorld().playSound(bossLoc, Sound.ENTITY_WITHER_SPAWN, 2.0f, 0.5f);
        bossLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, bossLoc, 150, 2.0, 2.0, 2.0, 0.05);

        // 주변 플레이어 경고
        bossLoc.getWorld().getNearbyPlayers(bossLoc, 20).forEach(player -> {
            Component finalWarning = Component.text("☠ ", NamedTextColor.DARK_RED)
                    .append(Component.text("보스가 마지막 힘을 끌어올립니다!", NamedTextColor.RED, TextDecoration.BOLD));

            player.sendMessage(finalWarning);
        });

        // 최종페이즈 증원군 소환
        for (int i = 0; i < 5; i++) {
            Location spawnLoc = bossLoc.clone().add((Math.random() * 6) - 3, 0, (Math.random() * 6) - 3);

            bossLoc.getWorld().spawn(spawnLoc, org.bukkit.entity.Zombie.class, zombie -> {
                zombie.customName(Component.text("보스의 부하", NamedTextColor.DARK_GRAY));
                zombie.setCustomNameVisible(true);
                zombie.getEquipment().setHelmet(new org.bukkit.inventory.ItemStack(org.bukkit.Material.IRON_HELMET));
            });
        }

        // 2초 후 번개 강림
        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            // 보스가 이미 죽었으면 스킬 취소
            if (!BossManager.hasBoss()) {
                return;
            }
            bossLoc.getWorld().getNearbyEntities(bossLoc, 10.0, 10.0, 10.0).forEach(entity -> {

                if (entity instanceof Player p) {
                    Component lastWords = Component.text("[보스 좀비] ", NamedTextColor.RED)
                            .append(Component.text("이것이 나의 마지막 힘이다...!", NamedTextColor.WHITE, TextDecoration.BOLD));

                    p.sendMessage(lastWords);
                    p.getWorld().strikeLightning(p.getLocation());
                }
            });
        }, 40L);
    }
}