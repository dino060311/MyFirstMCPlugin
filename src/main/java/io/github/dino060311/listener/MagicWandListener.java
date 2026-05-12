package io.github.dino060311.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class MagicWandListener implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final int COOLDOWN_TIME_SECONDS = 3;

    @EventHandler
    public void onWandUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            
            if (player.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD) {
                if (player.getInventory().getItemInMainHand().getItemMeta() != null &&
                    "§6[ 화염 마법 지팡이 ]".equals(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) {

                    UUID playerId = player.getUniqueId();
                    long currentTime = System.currentTimeMillis();

                    if (cooldowns.containsKey(playerId)) {
                        long lastUseTime = cooldowns.get(playerId);
                        long timePassed = (currentTime - lastUseTime) / 1000;

                        if (timePassed < COOLDOWN_TIME_SECONDS) {
                            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1.0f, 1.0f);
                            return; 
                        }
                    }

                    cooldowns.put(playerId, currentTime);
                    player.setCooldown(Material.BLAZE_ROD, COOLDOWN_TIME_SECONDS * 20);

                    player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0f, 1.0f);
                    player.playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1.0f, 0.5f); 
                    
                    Location loc = player.getEyeLocation(); 
                    Vector dir = loc.getDirection().normalize().multiply(0.4); 

                    // 파티클을 쏘면서 동시에 충돌 판정(Hitscan)을 진행
                    for (int i = 0; i < 50; i++) {
                        loc.add(dir); 
                        player.getWorld().spawnParticle(Particle.FLAME, loc, 3, 0.1, 0.1, 0.1, 0.01); 
                        if (i % 3 == 0) { 
                            player.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 1, 0, 0, 0, 0);
                        }

                        // 현재 파티클 위치 반경 0.5칸 안에 엔티티(몹/사람)가 있는지 검사!
                        for (Entity entity : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                            // 맞은 대상이 살아있는 생명체(LivingEntity)이고, 나 자신(player)이 아니라면?
                            if (entity instanceof LivingEntity && entity != player) {
                                LivingEntity target = (LivingEntity) entity;
                                
                                // 1. 데미지 5.0 (하트 2.5칸) 입히기
                                target.damage(5.0, player);
                                
                                // 2. 대상에게 3초(60틱) 동안 불(화상) 붙이기
                                target.setFireTicks(60);
                                
                                // 3. 타격음(성공 소리) 재생
                                player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
                                
                                return; 
                            }
                        }
                    }
                }
            }
        }
    }
}