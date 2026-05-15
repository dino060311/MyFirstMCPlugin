package io.github.dino060311.listener;

import io.github.dino060311.service.BombService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class BombListener implements Listener {

    private final BombService bombService;

    public BombListener(Plugin plugin) {
        this.bombService = new BombService(plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // 1. TNT인지 확인 + 아이템에 이름 데이터가 있는지 안전하게 확인
        if (item.getType() == Material.TNT && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {

            // 2. Component에서 순수 글자(Plain Text)만 추출하기!
            Component displayName = item.getItemMeta().displayName();
            String plainName = PlainTextComponentSerializer.plainText().serialize(displayName);

            // 순수 글자에 "시한폭탄"이 포함되어 있는지 확인
            if (plainName.contains("시한폭탄")) {

                // 2. 우클릭을 했는지 확인
                if (event.getAction().name().contains("RIGHT_CLICK")) {

                    // 우클릭 여러 번 방지를 위해 이벤트 취소 (블록이 설치되는 것을 막음)
                    event.setCancelled(true);

                    bombService.throwBomb(player, item);
                }
            }
        }
    }
}