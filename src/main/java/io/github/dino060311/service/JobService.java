package io.github.dino060311.service;

import io.github.dino060311.manager.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class JobService {

    private final RoleManager roleManager;

    public JobService(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public Map<UUID, String> assignJobs() {

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        // 1. 서버에서 미리 직업 배정표 작성 (섞기)
        Collections.shuffle(players);
        Map<UUID, String> results = new HashMap<>();
        results.put(players.get(0).getUniqueId(), "마피아");
        results.put(players.get(1).getUniqueId(), "경찰");
        results.put(players.get(2).getUniqueId(), "의사");
        for (int i = 3; i < players.size(); i++) {
            results.put(players.get(i).getUniqueId(), "시민");

        }

        // 2. 모든 플레이어에게 룰렛 시작
        for (Player player : players) {
            roleManager.setRole(player, results.get(player.getUniqueId()));
        }
        return results;
    }
}