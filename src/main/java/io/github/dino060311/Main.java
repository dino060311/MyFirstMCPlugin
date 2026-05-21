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
        getLogger().info("커스텀 게임 시스템이 성공적으로 로드되었습니다.");
        getLogger().info("Commands / Listeners / Services 초기화 완료");
        getLogger().info("------------------------------------");
    }

    @Override
    public void onDisable() {
        // 서버가 꺼질 때 실행되는 코드
        getLogger().info("플러그인이 안전하게 종료되었습니다.");
    }
}