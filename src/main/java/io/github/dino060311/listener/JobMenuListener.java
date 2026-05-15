package io.github.dino060311.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class JobMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        // 1. 인벤토리 제목을 Component로 가져오기
        Component titleComponent = event.getView().title();

        // 2. 제목에서 순수 글자만 추출 (색상 무시)
        String plainTitle = PlainTextComponentSerializer.plainText().serialize(titleComponent);

        // 3. 딱 "랜덤직업"만 들어있는지 확인
        if (plainTitle.contains("랜덤직업")) {

            // 아이템 클릭 취소
            event.setCancelled(true);
        }
    }
}