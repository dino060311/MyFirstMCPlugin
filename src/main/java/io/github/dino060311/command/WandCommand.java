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

public class WandCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {

            // 화염 마법 지팡이 생성
            ItemStack wand = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = wand.getItemMeta();
            meta.displayName(Component.text("[ 화염 마법 지팡이 ]", NamedTextColor.GOLD));
            wand.setItemMeta(meta);

            // 플레이어 지급
            player.getInventory().addItem(wand);

            Component message = Component.text("[System] ", NamedTextColor.GREEN)
                    .append(Component.text("화염 마법 지팡이를 획득했습니다!", NamedTextColor.WHITE));

            player.sendMessage(message);
            return true;
        }
        return false;
    }
}