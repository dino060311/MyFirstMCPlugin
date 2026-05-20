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

                if (sender instanceof Player player) {

                        // GUI 생성
                        Component guiTitle = Component.text("[ 직업 선택 메뉴 ]", NamedTextColor.DARK_GRAY);
                        Inventory gui = Bukkit.createInventory(null, 9, guiTitle);

                        // 직업 아이템 생성
                        ItemStack warriorItem = createJobItem(Material.IRON_SWORD, "[ 전사 ]", NamedTextColor.RED,
                                        "클릭하여 전사 직업을 선택합니다.", "철검");
                        ItemStack archerItem = createJobItem(Material.BOW, "[ 궁수 ]", NamedTextColor.GREEN,
                                        "클릭하여 궁수 직업을 선택합니다.", "활과 화살");

                        // GUI 슬롯 배치
                        gui.setItem(3, warriorItem);
                        gui.setItem(5, archerItem);

                        // GUI 열기
                        player.openInventory(gui);
                        return true;
                }
                return false;
        }

        // 직업 선택 GUI 아이템 생성 메서드
        private ItemStack createJobItem(Material material, String name, NamedTextColor color, String description,
                        String weapon) {

                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();

                // 이름 설정
                meta.displayName(Component.text(name, color).decoration(TextDecoration.ITALIC, false));

                // 설명 설정
                meta.lore(List.of(
                                Component.text(description, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,
                                                false),
                                Component.text("기본 무기: " + weapon, NamedTextColor.GRAY)
                                                .decoration(TextDecoration.ITALIC, false)));

                item.setItemMeta(meta);
                return item;
        }
}