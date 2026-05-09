package io.github.dino060311.items;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            
            // 블레이즈 막대기(BLAZE_ROD)를 마법 지팡이로 만듭니다.
            ItemStack wand = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = wand.getItemMeta();
            meta.setDisplayName("§6[ 화염 마법 지팡이 ]");
            wand.setItemMeta(meta);
            
            player.getInventory().addItem(wand);
            player.sendMessage("§a[System] §f화염 마법 지팡이를 획득했습니다!");
            return true;
        }
        return false;
    }
}