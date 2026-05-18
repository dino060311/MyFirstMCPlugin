package io.github.dino060311.listener;

import io.github.dino060311.manager.BossManager;
import io.github.dino060311.service.BossSkillService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.time.Duration;

public class BossListener implements Listener {

    private final BossSkillService skillService;

    public BossListener(BossSkillService skillService) {
        this.skillService = skillService;
    }

    @EventHandler
    public void onBossDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity boss && boss.customName() != null) {
            String name = PlainTextComponentSerializer.plainText().serialize(boss.customName());

            if (name.contains("보스 좀비")) {
                double maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                double currentHealth = boss.getHealth() - e.getFinalDamage();
                double healthPercent = currentHealth / maxHealth;

                BossManager.updateProgress(healthPercent);

                if (healthPercent <= 0.5 && name.contains("[1페이즈]")) {
                    // 체력이 50% 이하로 떨어졌고, 현재 이름이 1페이즈라면
                    boss.customName(Component.text("[2페이즈] 분노한 보스 좀비")
                            .color(NamedTextColor.GOLD)
                            .decorate(TextDecoration.BOLD));

                    // 페이즈 전환 연출
                    boss.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, boss.getLocation(), 3);
                    boss.getWorld().playSound(boss.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.5f, 1.0f);

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.showTitle(
                                Title.title(
                                        Component.text("⚠ PHASE 2 ⚠", NamedTextColor.GOLD),
                                        Component.text("보스 좀비가 분노합니다!", NamedTextColor.WHITE),
                                        Title.Times.times(
                                                Duration.ofMillis(500),
                                                Duration.ofSeconds(3),
                                                Duration.ofMillis(1000))));
                    }

                    // 스킬 발동 및 보스바 변경
                    skillService.areaPush(boss.getLocation());
                    BossManager.updatePhase("§6§l[2페이즈] 분노한 보스 좀비", BarColor.YELLOW);
                } else if (healthPercent <= 0.2 && !name.contains("[최종페이즈]")) {
                    // 체력이 20% 이하로 떨어졌고, 아직 최종페이즈로 변신하지 않았다면

                    // 좀비 머리 위 이름표 변경
                    boss.customName(Component.text("[최종페이즈] 각성한 보스 좀비")
                            .color(NamedTextColor.LIGHT_PURPLE)
                            .decorate(TextDecoration.BOLD));

                    // 최종페이즈 연출
                    boss.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, boss.getLocation(), 100);
                    boss.getWorld().playSound(boss.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2.0f, 0.8f);

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.showTitle(
                                Title.title(
                                        Component.text("☠ FINAL PHASE ☠", NamedTextColor.LIGHT_PURPLE),
                                        Component.text("보스가 각성했습니다!", NamedTextColor.LIGHT_PURPLE),
                                        Title.Times.times(
                                                Duration.ofMillis(500),
                                                Duration.ofSeconds(4),
                                                Duration.ofMillis(1000))));
                    }

                    // 스킬 발동 및 보스바 변경
                    skillService.lastResort(boss.getLocation());
                    BossManager.updatePhase("§d§l[최종페이즈] 각성한 보스 좀비", BarColor.PURPLE);
                }
            }
        }

    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent e) {
        if (e.getEntity().customName() != null) {
            String name = PlainTextComponentSerializer.plainText().serialize(e.getEntity().customName());
            if (name.contains("보스 좀비")) {
                BossManager.removeBossBar();
                BossManager.clearBoss();
            }
        }
    }
}