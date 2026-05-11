package io.github.dino060311;

import io.github.dino060311.manager.InitManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        InitManager.init(this);

        // 서버가 켜질 때 실행되는 코드
        getLogger().info("------------------------------------");
        getLogger().info("MyFirstMCPlugin 활성화 완료!");
        getLogger().info("첫 번째 마인크래프트 플러그인 제작에 성공했습니다.");
        getLogger().info("채팅 이벤트 리스너가 성공적으로 등록되었습니다.");
        getLogger().info("------------------------------------");
    }

    @Override
    public void onDisable() {
        // 서버가 꺼질 때 실행되는 코드
        getLogger().info("플러그인이 안전하게 종료되었습니다.");
    }
}