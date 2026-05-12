package io.github.dino060311.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BombCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            // 1. TNT 아이템 생성
            ItemStack bomb = new ItemStack(Material.TNT);
            ItemMeta meta = bomb.getItemMeta();

            // 2. 특별한 이름과 설명 추가 (이게 있어야 일반 TNT와 구분됨)
            meta.setDisplayName("§c§l시한폭탄");
            meta.setLore(Arrays.asList("§f우클릭 시 전방으로 날아갑니다.", "§73초 후 강력한 폭발이 일어납니다!"));
            bomb.setItemMeta(meta);

            // 3. 플레이어에게 지급
            player.getInventory().addItem(bomb);
            player.sendMessage("§a[System] §f시한폭탄을 획득했습니다!");
            return true;
        }
        return false;
    }
}