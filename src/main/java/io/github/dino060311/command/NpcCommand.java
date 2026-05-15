package io.github.dino060311.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class NpcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {

            // 1. 플레이어의 현재 위치에 주민 소환
            Villager npc = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);

            // 2. NPC 세팅
            Component npcName = Component.text("[목격자] 촌장", NamedTextColor.YELLOW);
            npc.customName(npcName); // 머리 위 이름표 설정
            npc.setCustomNameVisible(true); // 이름표가 항상 보이게 설정
            npc.setAI(false); // AI를 꺼서 움직이지 않게 고정
            npc.setInvulnerable(true); // 데미지를 입지 않는 무적 상태로 설정

            Component message = Component.text("[System] ", NamedTextColor.GREEN)
                    .append(Component.text("스토리 NPC가 소환되었습니다!", NamedTextColor.WHITE));
            player.sendMessage(message);

            return true;
        }
        return false;
    }
}