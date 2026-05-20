package io.github.dino060311.command;

import io.github.dino060311.manager.BossManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class BossSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {

            if (BossManager.hasBoss()) {
                p.sendMessage(Component.text("이미 보스 좀비가 존재합니다!", NamedTextColor.RED));
                return true;
            }

            Zombie boss = p.getWorld().spawn(p.getLocation(), Zombie.class);

            // 보스 이름 설정
            boss.customName(Component.text("[1페이즈] 강화된 보스 좀비")
                    .color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD));
            boss.setCustomNameVisible(true);
            boss.setAdult();
            boss.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
            boss.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));

            boss.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(2.0);
            boss.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1.0);
            boss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(150.0);
            boss.setHealth(150.0);

            // 현재 보스를 시스템에 등록
            BossManager.setCurrentBoss(boss);

            // 보스바 생성
            BossManager.createBossBar("§4§l[1페이즈] 강화된 보스 좀비");

            p.sendMessage("§c강력한 보스 좀비가 소환되었습니다!");
            return true;
        }
        return false;
    }
}