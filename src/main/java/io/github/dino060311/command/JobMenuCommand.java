package io.github.dino060311.command;

import io.github.dino060311.manager.RoleManager;
import io.github.dino060311.service.JobService;
import io.github.dino060311.service.RouletteService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class JobMenuCommand implements CommandExecutor {

    private final JobService jobService;
    private final RouletteService rouletteService;

    public JobMenuCommand(RoleManager roleManager, JobService jobService, RouletteService rouletteService) {
        this.jobService = jobService;
        this.rouletteService = rouletteService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Player> players = Bukkit.getOnlinePlayers().stream().collect(Collectors.toList());
        if (players.size() < 3) {
            sender.sendMessage("§c[System] 최소 3명이 필요합니다.");
            return true;
        }

        Map<UUID, String> results = jobService.assignJobs();

        // 2. 모든 플레이어에게 룰렛 시작
        for (Player p : Bukkit.getOnlinePlayers()) {
            rouletteService.startRoulette(p, results.get(p.getUniqueId()));
        }
        return true;
    }
}