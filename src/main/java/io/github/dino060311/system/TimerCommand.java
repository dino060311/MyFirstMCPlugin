package io.github.dino060311.system;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerCommand implements CommandExecutor {

    private Plugin plugin;

    // 플러그인 메인 클래스를 받아오기 위한 생성자
    public TimerCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        // 10초 타이머 세팅
        int timeLimit = 10;

        // 1. 화면 상단에 띄울 보스바 생성
        BossBar bossBar = Bukkit.createBossBar("§a[파밍 시간] §f남은 시간: " + timeLimit + "초", BarColor.GREEN, BarStyle.SOLID);

        // 2. 서버에 접속한 '모든' 플레이어에게 이 보스바를 띄워줍니다.
        for (Player p : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(p);
        }

        // 3. 타이머 시작
        new BukkitRunnable() {
            int timeLeft = timeLimit;

            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--; // 1초씩 감소
                    
                    // 글자 업데이트
                    bossBar.setTitle("§a[파밍 시간] §f남은 시간: " + timeLeft + "초");
                    
                    // 게이지 바 업데이트
                    bossBar.setProgress((double) timeLeft / timeLimit); 

                    // 5초 이하로 남으면 긴장감을 위해 색상을 빨간색으로 변경!
                    if (timeLeft <= 5) {
                        bossBar.setColor(BarColor.RED);
                        bossBar.setTitle("§c[파밍 시간] §f남은 시간: " + timeLeft + "초");
                    }
                } else {
                    // 0초가 되면 실행될 코드
                    bossBar.removeAll(); // 모든 플레이어 화면에서 보스바 삭제
                    Bukkit.broadcastMessage("§c[System] §f파밍 시간이 종료되었습니다!");
                    this.cancel(); // 타이머 완전 종료
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // 0틱 후 시작, 1초(20틱)마다 반복 실행

        Bukkit.broadcastMessage("§a[System] §f타이머가 시작되었습니다.");
        return true;
    }
}