package io.github.dino060311.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // 1. 플레이어가 들고 있는 아이템이 '막대기(STICK)'인지 확인
        if (player.getInventory().getItemInMainHand().getType() == Material.STICK) {

            // 2. 우클릭(공중 혹은 블록)을 했는지 확인
            if (event.getAction().name().contains("RIGHT_CLICK")) {

                // 3. 플레이어가 바라보고 있는 위치(최대 100칸)를 가져옴
                org.bukkit.block.Block targetBlock = player.getTargetBlockExact(100);

                if (targetBlock != null) {
                    // 4. 해당 위치에 번개를 소환!
                    player.getWorld().strikeLightning(targetBlock.getLocation());

                    // 5. 효과음과 메시지 추가
                    Component magicMsg = Component.text("[Magic] ", NamedTextColor.GOLD)
                            .append(Component.text("번개 지팡이를 사용했습니다!", NamedTextColor.WHITE));
                    player.sendMessage(magicMsg);
                }
            }
        }
    }
}