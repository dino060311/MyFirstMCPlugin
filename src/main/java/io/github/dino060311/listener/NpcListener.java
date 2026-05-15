package io.github.dino060311.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class NpcListener implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickedEntity = event.getRightClicked();

        // 1. 클릭한 엔티티가 '주민'인지 확인
        if (clickedEntity instanceof Villager npc) {

            // 2. 그 주민의 이름이 우리가 만든 NPC인지 확인
            Component nameComponent = npc.customName();
            if (nameComponent == null) return;

            String plainName = PlainTextComponentSerializer.plainText().serialize(nameComponent);

            // 이름에 "[목격자] 촌장"이 포함되어 있는지 확인
            if (plainName.contains("[목격자] 촌장")) {
                
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
                    player.sendMessage(""); // 빈 줄
                    Component successMsg1 = Component.text("[목격자] 촌장: ", NamedTextColor.YELLOW)
                            .append(Component.text("오! 이 달콤한 냄새는 케이크?! 정말 고맙네!", NamedTextColor.WHITE));
                    Component successMsg2 = Component.text("[목격자] 촌장: ", NamedTextColor.YELLOW)
                            .append(Component.text("보답으로 내가 아끼던 ", NamedTextColor.WHITE))
                            .append(Component.text("[다이아몬드]", NamedTextColor.AQUA))
                            .append(Component.text("를 주지. 흠흠!", NamedTextColor.WHITE));
                    
                    player.sendMessage(successMsg1);
                    player.sendMessage(successMsg2);
                    player.sendMessage(""); // 빈 줄
                    
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

                } else {
                    // 케이크를 안 들고 있을 때
                    player.sendMessage(""); // 빈 줄
                    Component failMsg = Component.text("[목격자] 촌장: ", NamedTextColor.YELLOW)
                            .append(Component.text("에구구... 배가 너무 고파서 말이 안 나오는구만.", NamedTextColor.WHITE));
                    Component tipMsg = Component.text("(촌장이 달콤하고 부드러운 ", NamedTextColor.GRAY)
                            .append(Component.text("[케이크]", NamedTextColor.RED))
                            .append(Component.text("를 먹고 싶어 하는 눈치입니다.)", NamedTextColor.GRAY));
                    
                    player.sendMessage(failMsg);
                    player.sendMessage(tipMsg);
                    player.sendMessage(""); // 빈 줄
                    
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
        }
    }
}