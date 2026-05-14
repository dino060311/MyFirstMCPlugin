package io.github.dino060311.service;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class BossSkillService {
    // 1페이즈: 광역 밀치기 스킬
    public void areaPush(Location bossLoc) {
        bossLoc.getWorld().spawnParticle(Particle.EXPLOSION, bossLoc, 10);
        bossLoc.getWorld().playSound(bossLoc, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

        bossLoc.getWorld().getNearbyEntities(bossLoc, 5.0, 5.0, 5.0).forEach(entity -> {
            if (entity instanceof Player p) {
                p.setVelocity(p.getLocation().toVector().subtract(bossLoc.toVector()).normalize().multiply(2));
                p.sendMessage("§c[보스 좀비] §f저리 비켜라!");
            }

        });

    }

    public void lastResort(Location bossLoc) {
        bossLoc.getWorld().playSound(bossLoc, Sound.ENTITY_WITHER_SPAWN, 2.0f, 0.5f); // 웅장한 소리
        bossLoc.getWorld().spawnParticle(Particle.FLAME, bossLoc, 100); // 화염 파티클

        // 주변 10칸 안의 플레이어들에게 번개 소환
        bossLoc.getWorld().getNearbyEntities(bossLoc, 10.0, 10.0, 10.0).forEach(entity -> {
            if (entity instanceof Player p) {
                p.sendMessage("§c[보스 좀비] §4§l이것이 나의 마지막 힘이다...!");
                // 플레이어 위치에 번개 소환 (실제 데미지도 들어감)
                p.getWorld().strikeLightning(p.getLocation());
            }
        });
    }
}