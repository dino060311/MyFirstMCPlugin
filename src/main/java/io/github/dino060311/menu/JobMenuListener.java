package io.github.dino060311.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class JobMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // 룰렛 창 이름과 똑같이 맞춰주세요! (아까 코드에서 룰렛 창 이름을 "§0??? 직업 추첨 중 ???" 로 했었죠?)
        if (event.getView().getTitle().contains("랜덤직업")) {
            
            // ❌ 뽑기 도중 아이템을 클릭하거나 빼가는 모든 행위를 차단!
            event.setCancelled(true);
        }
    }
}