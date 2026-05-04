package io.github.dino060311; // pom.xml에서 설정한 groupId와 맞춰야 해요

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // 서버가 켜질 때 실행되는 코드
        getLogger().info("------------------------------------");
        getLogger().info("MyFirstMCPlugin 활성화 완료!");
        getLogger().info("첫 번째 마인크래프트 플러그인 제작에 성공했습니다.");
        getLogger().info("------------------------------------");

        // 리스너 등록
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new BombListener(this), this);
        
        // 메뉴 명령어와 리스너 등록
        getCommand("메뉴").setExecutor(new MenuCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        // 보스바 타이머 명령어 등록
        getCommand("타이머").setExecutor(new TimerCommand(this));

        // NPC 명령어와 상호작용 리스너 등록
        getCommand("npc소환").setExecutor(new NpcCommand());
        getServer().getPluginManager().registerEvents(new NpcListener(), this);

        getLogger().info("채팅 이벤트 리스너가 성공적으로 등록되었습니다.");
    }

    @Override
    public void onDisable() {
        // 서버가 꺼질 때 실행되는 코드
        getLogger().info("플러그인이 안전하게 종료되었습니다.");
    }
}