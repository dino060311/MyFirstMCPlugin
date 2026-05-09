package io.github.dino060311.system;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 화면 중앙에 큰 제목과 작은 부제목을 띄웁니다.
        // 순서: 제목, 부제목, 나타나는 시간, 머무는 시간, 사라지는 시간 (틱 단위: 20틱 = 1초)
        player.sendTitle("§a[ Welcome ]", "§f" + player.getName() + "님, 환영합니다!", 10, 70, 20);
        
        // 환영 메시지도 보냅니다.
        player.sendMessage("§7[System] 서버의 특수 능력을 테스트해보세요!");
    }
}