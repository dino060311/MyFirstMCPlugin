package io.github.dino060311;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.dino060311.menu.*;
import io.github.dino060311.npc.*;
import io.github.dino060311.command.HelpCommand;
import io.github.dino060311.cutscene.*;
import io.github.dino060311.items.*;
import io.github.dino060311.system.*;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // 서버가 켜질 때 실행되는 코드
        getLogger().info("------------------------------------");
        getLogger().info("MyFirstMCPlugin 활성화 완료!");
        getLogger().info("첫 번째 마인크래프트 플러그인 제작에 성공했습니다.");
        getLogger().info("채팅 이벤트 리스너가 성공적으로 등록되었습니다.");
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

        //투척형 시한폭탄 등록
        getCommand("시한폭탄").setExecutor(new BombCommand());

        // 마법 지팡이 기능 등록
        getCommand("마법지팡이").setExecutor(new WandCommand());
        getServer().getPluginManager().registerEvents(new MagicWandListener(), this);

        // 컷신 연출 기능 등록
        getCommand("컷신").setExecutor(new CutsceneCommand(this));
        getServer().getPluginManager().registerEvents(new CutsceneListener(), this);

        //직업 선택 GUI 기능 등록
        getCommand("랜덤직업").setExecutor(new JobMenuCommand(this));
        getServer().getPluginManager().registerEvents(new JobMenuListener(), this);

        getCommand("무기대장장이").setExecutor(new MerchantNpcCommand());
        getServer().getPluginManager().registerEvents(new MerchantNpcListener(), this);

        getCommand("서버명령어").setExecutor(new HelpCommand());

    }

    @Override
    public void onDisable() {
        // 서버가 꺼질 때 실행되는 코드
        getLogger().info("플러그인이 안전하게 종료되었습니다.");
    }
}