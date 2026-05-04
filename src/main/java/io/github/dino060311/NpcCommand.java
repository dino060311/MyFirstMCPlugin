package io.github.dino060311;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class NpcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // 1. 플레이어의 현재 위치에 주민 소환
            Villager npc = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);

            // 2. NPC 세팅
            npc.setCustomName("§e[목격자] 촌장"); // 머리 위 이름표 설정
            npc.setCustomNameVisible(true);      // 이름표가 항상 보이게 설정
            npc.setAI(false);                    // AI를 꺼서 움직이지 않게 고정
            npc.setInvulnerable(true);           // 데미지를 입지 않는 무적 상태로 설정

            player.sendMessage("§a[System] §f스토리 NPC가 소환되었습니다!");
            return true;
        }
        return false;
    }
}