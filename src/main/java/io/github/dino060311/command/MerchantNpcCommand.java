package io.github.dino060311.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class MerchantNpcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            
            // 1. 플레이어 위치에 주민 소환
            Villager merchant = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
            
            // 2. NPC 설정
            merchant.setCustomName("§e무기 대장장이");
            merchant.setCustomNameVisible(true); // 이름표 항상 보이게
            merchant.setAI(false); // 가만히 서 있게 고정
            merchant.setInvulnerable(true); // 무적 설정
            
            merchant.setProfession(Villager.Profession.WEAPONSMITH);

            player.sendMessage("§a[System] §e무기 대장장이§f를 소환했습니다!");
            return true;
        }
        return false;
    }
}