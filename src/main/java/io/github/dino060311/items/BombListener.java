package io.github.dino060311.items;

import io.github.dino060311.service.BombService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BombListener implements Listener {

    private final BombService bombService;

    public BombListener(Plugin plugin) {
        this.bombService = new BombService(plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // 1. TNT인지 확인 + 이름이 "§c§l시한폭탄"인지 확인
        if (item.getType() == Material.TNT && item.hasItemMeta() &&
                "§c§l시한폭탄".equals(item.getItemMeta().getDisplayName())) {

            // 2. 우클릭을 했는지 확인
            if (event.getAction().name().contains("RIGHT_CLICK")) {

                // 우클릭 여러 번 방지를 위해 이벤트 취소 (블록이 설치되는 것을 막음)
                event.setCancelled(true);

                bombService.throwBomb(player, item);
            }
        }
    }
}