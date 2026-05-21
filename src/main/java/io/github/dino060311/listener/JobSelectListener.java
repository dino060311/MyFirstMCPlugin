package io.github.dino060311.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class JobSelectListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // 1. 클릭한 인벤토리의 이름이 우리가 만든 메뉴인지 확인
        Component titleComponent = event.getView().title();
        String plainTitle = PlainTextComponentSerializer.plainText().serialize(titleComponent);

        // 제목이 "[ 직업 선택 메뉴 ]"인지 확인
        if (plainTitle.equals("[ 직업 선택 메뉴 ]")) {

            // 2. 플레이어가 아이템을 드래그해서 가져가는 것을 막음
            event.setCancelled(true);

            // 빈 공간을 클릭했으면 무시
            if (event.getCurrentItem() == null
                    || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            Material clickedType = event.getCurrentItem().getType();

            // 3. 클릭한 아이템에 따라 직업 지급
            if (clickedType == Material.IRON_SWORD) {
                // 전사 선택 시
                player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));

                Component warriorMsg = Component.text("[System] ", NamedTextColor.RED)
                        .append(Component.text("'전사' 직업을 선택하셨습니다!", NamedTextColor.WHITE));
                player.sendMessage(warriorMsg);

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                player.closeInventory();

            } else if (clickedType == Material.BOW) {
                // 궁수 선택 시
                player.getInventory().addItem(new ItemStack(Material.BOW));
                player.getInventory().addItem(new ItemStack(Material.ARROW, 64)); // 화살 64개

                Component archerMsg = Component.text("[System] ", NamedTextColor.GREEN)
                        .append(Component.text("'궁수' 직업을 선택하셨습니다!", NamedTextColor.WHITE));
                player.sendMessage(archerMsg);

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                player.closeInventory();
            }
        }
    }
}