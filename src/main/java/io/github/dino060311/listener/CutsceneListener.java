package io.github.dino060311.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CutsceneListener implements Listener {
    
    // 화면이 고정된 플레이어들의 고유번호(UUID)를 담아둘 보관소
    public static final Set<UUID> frozenPlayers = new HashSet<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // 만약 이 플레이어가 '얼어붙은 명단'에 들어있다면?
        if (frozenPlayers.contains(event.getPlayer().getUniqueId())) {
            
            Location from = event.getFrom();
            Location to = event.getTo();
            
            // X, Y, Z 위치뿐만 아니라 Yaw(좌우 시선), Pitch(상하 시선)가 조금이라도 변하면
            if (to != null && (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ() 
                    || from.getYaw() != to.getYaw() || from.getPitch() != to.getPitch())) {
                
                // 움직임을 강제로 취소시킵니다!
                event.setCancelled(true);
            }
        }
    }
}