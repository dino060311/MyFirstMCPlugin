package io.github.dino060311.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import java.time.Duration;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 1. 타이틀 글자 설정
        // 화면 중앙에 큰 제목과 작은 부제목을 띄웁니다.
        Component mainTitle = Component.text("[ Welcome ]", NamedTextColor.GREEN);
        Component subTitle = Component.text(player.getName() + "님, 환영합니다!", NamedTextColor.WHITE);

        // 2. 시간 설정
        Title.Times times = Title.Times.times(
                Duration.ofMillis(500), // 나타나는 시간
                Duration.ofMillis(3500), // 머무는 시간
                Duration.ofMillis(1000) // 사라지는 시간
        );

        // 3. 화면에 타이틀 띄우기
        Title title = Title.title(mainTitle, subTitle, times);
        player.showTitle(title);

        // 환영 메시지도 보냅니다.
        player.sendMessage(Component.text("[System] ", NamedTextColor.GRAY)
                .append(Component.text("서버의 특수 능력을 테스트해보세요!", NamedTextColor.WHITE)));

        player.sendMessage(Component.text("[Tip] ", NamedTextColor.YELLOW)
                .append(Component.text("도움말이 필요하면 ", NamedTextColor.WHITE))
                .append(Component.text("/서버명령어 ", NamedTextColor.GREEN))
                .append(Component.text("를 입력하세요.", NamedTextColor.WHITE)));
    }
}