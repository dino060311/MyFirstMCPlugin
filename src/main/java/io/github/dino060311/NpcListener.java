package io.github.dino060311;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class NpcListener implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickedEntity = event.getRightClicked();

        // 1. 클릭한 엔티티가 '주민'인지 확인
        if (clickedEntity instanceof Villager) {
            Villager npc = (Villager) clickedEntity;

            // 2. 그 주민의 이름이 우리가 만든 NPC인지 확인
            if (npc.getCustomName() != null && npc.getCustomName().equals("§e[목격자] 촌장")) {
                
                // 주민 기본 거래 창이 열리는 것을 막음
                event.setCancelled(true);

                // 3. 플레이어가 현재 손에 들고 있는 아이템 가져오기
                ItemStack itemInHand = player.getInventory().getItemInMainHand();

                // 4. 손에 든 아이템이 '케이크'인지 검사
                if (itemInHand.getType() == Material.CAKE) {
                    
                    // 케이크 1개 빼앗기
                    itemInHand.setAmount(itemInHand.getAmount() - 1);
                    
                    // 다이아몬드 1개 지급
                    player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                    
                    // 성공 대사 및 효과음
                    player.sendMessage("");
                    player.sendMessage("§e[목격자] 촌장: §f오! 이 달콤한 냄새는 케이크?! 정말 고맙네!");
                    player.sendMessage("§e[목격자] 촌장: §f보답으로 내가 아끼던 §b[다이아몬드]§f를 주지. 흠흠!");
                    player.sendMessage("");
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

                } else {
                    // 케이크를 안 들고 있을 때
                    player.sendMessage("");
                    player.sendMessage("§e[목격자] 촌장: §f에구구... 배가 너무 고파서 말이 안 나오는구만.");
                    player.sendMessage("§7(촌장이 달콤하고 부드러운 §c[케이크]§7를 먹고 싶어 하는 눈치입니다.)");
                    player.sendMessage("");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
        }
    }
}