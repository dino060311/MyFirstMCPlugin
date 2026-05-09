package io.github.dino060311.menu;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // 1. 9칸짜리 가상의 인벤토리(GUI) 생성
            Inventory gui = Bukkit.createInventory(null, 9, "§8[ 직업 선택 메뉴 ]");

            // 2. '전사' 아이템 세팅 (철검)
            ItemStack warriorItem = new ItemStack(Material.IRON_SWORD);
            ItemMeta warriorMeta = warriorItem.getItemMeta();
            warriorMeta.setDisplayName("§c[ 전사 ]");
            warriorMeta.setLore(Arrays.asList("§f클릭하여 전사 직업을 선택합니다.", "§7기본 무기: 철검"));
            warriorItem.setItemMeta(warriorMeta);

            // 3. '궁수' 아이템 세팅 (활)
            ItemStack archerItem = new ItemStack(Material.BOW);
            ItemMeta archerMeta = archerItem.getItemMeta();
            archerMeta.setDisplayName("§a[ 궁수 ]");
            archerMeta.setLore(Arrays.asList("§f클릭하여 궁수 직업을 선택합니다.", "§7기본 무기: 활과 화살"));
            archerItem.setItemMeta(archerMeta);

            // 4. GUI의 원하는 칸(0~8)에 아이템 배치
            gui.setItem(3, warriorItem); // 4번째 칸
            gui.setItem(5, archerItem);  // 6번째 칸

            // 5. 플레이어에게 GUI 열어주기
            player.openInventory(gui);
            return true;
        }
        return false;
    }
}