package io.github.dino060311.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.bossbar.BossBar;

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
        Component initialTitle = Component.text("[파밍 시간] ", NamedTextColor.GREEN)
                .append(Component.text("남은 시간: " + timeLimit + "초", NamedTextColor.WHITE));

        // 진행도(1.0f = 100%), 색상, 스타일 설정
        BossBar bossBar = BossBar.bossBar(initialTitle, 1.0f, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS);

        // 2. 서버에 접속한 '모든' 플레이어에게 이 보스바를 띄워줍니다.
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showBossBar(bossBar);
        }

        // 3. 타이머 시작
        new BukkitRunnable() {
            int timeLeft = timeLimit;

            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--; // 1초씩 감소

                    // 게이지 바 업데이트
                    float progress = (float) timeLeft / timeLimit;
                    bossBar.progress(progress);

                    // 5초 이하로 남으면 긴장감을 위해 색상을 빨간색으로 변경!
                    if (timeLeft <= 5) {
                        bossBar.color(BossBar.Color.RED); // setColor -> color
                        Component alertTitle = Component.text("[파밍 시간] ", NamedTextColor.RED)
                                .append(Component.text("남은 시간: " + timeLeft + "초", NamedTextColor.WHITE));
                        bossBar.name(alertTitle); // setTitle -> name
                    } else {
                        // 5초 초과일 때 기본 글자 업데이트
                        Component normalTitle = Component.text("[파밍 시간] ", NamedTextColor.GREEN)
                                .append(Component.text("남은 시간: " + timeLeft + "초", NamedTextColor.WHITE));
                        bossBar.name(normalTitle);
                    }
                } else {
                    // 0초가 되면 실행될 코드
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.hideBossBar(bossBar);
                    }

                    Component endMessage = Component.text("[System] ", NamedTextColor.RED)
                            .append(Component.text("파밍 시간이 종료되었습니다!", NamedTextColor.WHITE));
                    Bukkit.broadcast(endMessage);

                    this.cancel(); // 타이머 완전 종료
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // 0틱 후 시작, 1초(20틱)마다 반복 실행

        Component startMessage = Component.text("[System] ", NamedTextColor.GREEN)
                .append(Component.text("타이머가 시작되었습니다.", NamedTextColor.WHITE));
        Bukkit.broadcast(startMessage);

        return true;
    }
}