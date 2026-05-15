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
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.HashMap;
import java.util.UUID;

public class MagicWandListener implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final int COOLDOWN_TIME_SECONDS = 3;

    @EventHandler
    public void onWandUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // 1. 우클릭 확인
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            // 2. 아이템이 블레이즈 막대이고 메타 데이터(이름 등)가 있는지 확인
            if (item.getType() == Material.BLAZE_ROD && item.hasItemMeta()) {

                // 3. 아이템 이름을 Component로 가져와서 순수 글자만 추출
                Component nameComponent = item.getItemMeta().displayName();
                if (nameComponent == null)
                    return;

                String plainName = PlainTextComponentSerializer.plainText().serialize(nameComponent);

                // 이름에 "화염 마법 지팡이"가 포함되어 있는지 확인
                if (plainName.contains("화염 마법 지팡이")) {

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

                    // 쿨타임 적용
                    cooldowns.put(playerId, currentTime);
                    player.setCooldown(Material.BLAZE_ROD, COOLDOWN_TIME_SECONDS * 20);

                    // 마법 발사 효과음
                    player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0f, 1.0f);
                    player.playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1.0f, 0.5f);

                    Location loc = player.getEyeLocation();
                    Vector dir = loc.getDirection().normalize().multiply(0.4);

                    // 파티클을 쏘면서 동시에 충돌 판정(Hitscan)을 진행
                    for (int i = 0; i < 50; i++) {
                        loc.add(dir);
                        player.getWorld().spawnParticle(Particle.FLAME, loc, 3, 0.1, 0.1, 0.1, 0.01);
                        if (i % 3 == 0) {
                            player.getWorld().spawnParticle(Particle.LARGE_SMOKE, loc, 1, 0, 0, 0, 0);
                        }

                        // 충돌 판정
                        for (Entity entity : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                            if (entity instanceof LivingEntity target && entity != player) {
                                
                                // 데미지 및 화상 효과
                                target.damage(5.0, player);
                                target.setFireTicks(60);

                                // 타격음 재생
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