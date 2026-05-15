package io.github.dino060311.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class MerchantNpcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            
            // 1. 플레이어 위치에 주민 소환
            Villager merchant = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
            
            // 2. NPC 설정
            Component npcName = Component.text("무기 대장장이", NamedTextColor.YELLOW);
            merchant.customName(npcName);
            
            merchant.setCustomNameVisible(true); // 이름표 항상 보이게
            merchant.setAI(false); // 가만히 서 있게 고정
            merchant.setInvulnerable(true); // 무적 설정
            
            merchant.setProfession(Villager.Profession.WEAPONSMITH);

            Component message = Component.text("[System] ", NamedTextColor.GREEN)
                    .append(Component.text("무기 대장장이", NamedTextColor.YELLOW))
                    .append(Component.text("를 소환했습니다!", NamedTextColor.WHITE));
            player.sendMessage(message);
            
            return true;
        }
        return false;
    }
}