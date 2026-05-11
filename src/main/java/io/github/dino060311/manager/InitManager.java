package io.github.dino060311.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import io.github.dino060311.Main;
import io.github.dino060311.command.HelpCommand;
import io.github.dino060311.cutscene.*;
import io.github.dino060311.items.*;
import io.github.dino060311.menu.*;
import io.github.dino060311.npc.*;
import io.github.dino060311.service.*;
import io.github.dino060311.system.*;

public class InitManager {

    public static void init(Main plugin) {

        RoleManager roleManager = new RoleManager();
        JobService jobService = new JobService(roleManager);
        RouletteService rouletteService = new RouletteService(plugin);

        registerCommands(plugin, roleManager, jobService, rouletteService);
        registerEvents(plugin, roleManager, jobService, rouletteService);
    }

    private static void registerCommands(Main plugin, RoleManager roleManager, JobService jobService,
            RouletteService rouletteService) {

        // 명령어 등록
        plugin.getCommand("메뉴").setExecutor(new MenuCommand());
        plugin.getCommand("타이머").setExecutor(new TimerCommand(plugin));
        plugin.getCommand("npc소환").setExecutor(new NpcCommand());
        plugin.getCommand("시한폭탄").setExecutor(new BombCommand());
        plugin.getCommand("마법지팡이").setExecutor(new WandCommand());
        plugin.getCommand("컷신").setExecutor(new CutsceneCommand(plugin));
        plugin.getCommand("랜덤직업").setExecutor(new JobMenuCommand(roleManager, jobService, rouletteService));
        plugin.getCommand("무기대장장이").setExecutor(new MerchantNpcCommand());
        plugin.getCommand("서버명령어").setExecutor(new HelpCommand());
    }

    private static void registerEvents(Main plugin, RoleManager roleManager, JobService jobService,
            RouletteService rouletteService) {
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
        
        // 직업 시스템 관련
        pm.registerEvents(new MenuListener(), plugin);
        pm.registerEvents(new JobMenuListener(), plugin);
    }
}