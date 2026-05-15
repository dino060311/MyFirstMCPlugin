package io.github.dino060311.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import java.util.List;

public class BombCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            // 1. TNT 아이템 생성
            ItemStack bomb = new ItemStack(Material.TNT);
            ItemMeta meta = bomb.getItemMeta();

            // 2. 특별한 이름과 설명 추가 (최신 Component API 적용, 글자 내용 동일)
            
            // §c§l시한폭탄 (빨간색 굵게)
            Component name = Component.text("시한폭탄")
                    .color(NamedTextColor.RED)
                    .decorate(TextDecoration.BOLD)
                    .decoration(TextDecoration.ITALIC, false); // 기본 기울임꼴 방지
            meta.displayName(name);

            // §f우클릭 시 전방으로 날아갑니다. (흰색)
            Component lore1 = Component.text("우클릭 시 전방으로 날아갑니다.")
                    .color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false);
                    
            // §73초 후 강력한 폭발이 일어납니다! (회색)
            Component lore2 = Component.text("3초 후 강력한 폭발이 일어납니다!")
                    .color(NamedTextColor.GRAY)
                    .decoration(TextDecoration.ITALIC, false);
            
            // 설명(Lore) 적용
            meta.lore(List.of(lore1, lore2));
            
            bomb.setItemMeta(meta);

            // 3. 플레이어에게 지급
            player.getInventory().addItem(bomb);

            // §a[System] §f시한폭탄을 획득했습니다! (채팅 메시지)
            Component message = Component.text("[System] ", NamedTextColor.GREEN)
                    .append(Component.text("시한폭탄을 획득했습니다!", NamedTextColor.WHITE));
            player.sendMessage(message);

            return true;
        }
        return false;
    }
}