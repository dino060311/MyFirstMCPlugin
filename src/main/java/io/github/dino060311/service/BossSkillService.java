package io.github.dino060311.service;

import io.github.dino060311.Main;
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
            player.sendMessage("§6⚠ §c보스가 힘을 모으고 있습니다! 피하세요!");
        });

        // 2초 뒤 폭발
        Bukkit.getScheduler().runTaskLater(plugin, () -> {

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
            player.sendMessage("§4☠ §c§l보스가 마지막 힘을 끌어올립니다!");
        });

        // 2초 후 번개 강림
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            bossLoc.getWorld().getNearbyEntities(bossLoc, 10.0, 10.0, 10.0).forEach(entity -> {

                if (entity instanceof Player p) {
                    p.sendMessage("§c[보스 좀비] §4§l이것이 나의 마지막 힘이다...!");
                    p.getWorld().strikeLightning(p.getLocation());
                }
            });
        }, 40L);
    }
}