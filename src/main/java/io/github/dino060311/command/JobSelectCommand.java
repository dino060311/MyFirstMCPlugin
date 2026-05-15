package io.github.dino060311.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import java.util.List;

public class JobSelectCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // 1. 9칸짜리 가상의 인벤토리(GUI) 생성
            Component guiTitle = Component.text("[ 직업 선택 메뉴 ]", NamedTextColor.DARK_GRAY);
            Inventory gui = Bukkit.createInventory(null, 9, guiTitle);

            // 2. '전사' 아이템 세팅 (철검)
            ItemStack warriorItem = new ItemStack(Material.IRON_SWORD);
            ItemMeta warriorMeta = warriorItem.getItemMeta();

            Component warriorName = Component.text("[ 전사 ]", NamedTextColor.RED)
                    .decoration(TextDecoration.ITALIC, false);
            warriorMeta.displayName(warriorName);

            Component warriorLore1 = Component.text("클릭하여 전사 직업을 선택합니다.", NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false);
            Component warriorLore2 = Component.text("기본 무기: 철검", NamedTextColor.GRAY)
                    .decoration(TextDecoration.ITALIC, false);
            warriorMeta.lore(List.of(warriorLore1, warriorLore2));

            warriorItem.setItemMeta(warriorMeta);

            // 3. '궁수' 아이템 세팅 (활)
            ItemStack archerItem = new ItemStack(Material.BOW);
            ItemMeta archerMeta = archerItem.getItemMeta();

            Component archerName = Component.text("[ 궁수 ]", NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC, false);
            archerMeta.displayName(archerName);

            Component archerLore1 = Component.text("클릭하여 궁수 직업을 선택합니다.", NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false);
            Component archerLore2 = Component.text("기본 무기: 활과 화살", NamedTextColor.GRAY)
                    .decoration(TextDecoration.ITALIC, false);
            archerMeta.lore(List.of(archerLore1, archerLore2));

            archerItem.setItemMeta(archerMeta);

            // 4. GUI의 원하는 칸(0~8)에 아이템 배치
            gui.setItem(3, warriorItem); // 4번째 칸
            gui.setItem(5, archerItem); // 6번째 칸

            // 5. 플레이어에게 GUI 열어주기
            player.openInventory(gui);
            return true;
        }
        return false;
    }
}