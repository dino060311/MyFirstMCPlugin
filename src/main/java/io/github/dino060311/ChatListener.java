package io.github.dino060311; // 현지 님의 정확한 패키지 경로

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // 플레이어가 친 채팅 메시지 가져오기
        String message = event.getMessage();
        
        // 메시지에 "안녕"이 포함되어 있는지 확인
        if (message.contains("안녕")) {
            Player player = event.getPlayer();
            // 서버가 해당 플레이어에게 메시지 보내기
            player.sendMessage("§e[System] §f반갑습니다. 서버 플러그인이 정상 작동 중입니다.");
        }
    }
}