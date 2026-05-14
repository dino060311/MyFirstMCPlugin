package io.github.dino060311.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import io.github.dino060311.Main;
import io.github.dino060311.command.*;
import io.github.dino060311.listener.*;
import io.github.dino060311.service.*;

public class InitManager {

    public static void init(Main plugin) {

        RoleManager roleManager = new RoleManager();
        JobService jobService = new JobService(roleManager);
        RouletteService rouletteService = new RouletteService(plugin);
        BossSkillService bossSkillService = new BossSkillService();

        registerCommands(plugin, roleManager, jobService, rouletteService);
        registerEvents(plugin, roleManager, jobService, rouletteService, bossSkillService);
    }

    private static void registerCommands(Main plugin, RoleManager roleManager, JobService jobService,
            RouletteService rouletteService) {

        // 명령어 등록
        plugin.getCommand("직업선택").setExecutor(new JobSelectCommand());
        plugin.getCommand("타이머").setExecutor(new TimerCommand(plugin));
        plugin.getCommand("npc소환").setExecutor(new NpcCommand());
        plugin.getCommand("시한폭탄").setExecutor(new BombCommand());
        plugin.getCommand("마법지팡이").setExecutor(new WandCommand());
        plugin.getCommand("컷신").setExecutor(new CutsceneCommand(plugin));
        plugin.getCommand("랜덤직업").setExecutor(new JobMenuCommand(roleManager, jobService, rouletteService));
        plugin.getCommand("무기대장장이").setExecutor(new MerchantNpcCommand());
        plugin.getCommand("서버명령어").setExecutor(new HelpCommand());
        plugin.getCommand("보스좀비소환").setExecutor(new BossSpawnCommand());
    }

    private static void registerEvents(Main plugin, RoleManager roleManager, JobService jobService,
            RouletteService rouletteService, BossSkillService bossSkillService) {
        PluginManager pm = Bukkit.getPluginManager();

        // 시스템 관련
        pm.registerEvents(new ChatListener(plugin), plugin);
        pm.registerEvents(new JoinListener(), plugin);

        // 아이템 및 기능 관련
        pm.registerEvents(new BombListener(plugin), plugin);
        pm.registerEvents(new NpcListener(), plugin);
        pm.registerEvents(new MagicWandListener(), plugin);
        pm.registerEvents(new CutsceneListener(), plugin);
        pm.registerEvents(new MerchantNpcListener(), plugin);
        pm.registerEvents(new BossListener(bossSkillService), plugin);

        // 직업 시스템 관련
        pm.registerEvents(new JobSelectListener(), plugin);
        pm.registerEvents(new JobMenuListener(), plugin);
    }
}