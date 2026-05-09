package io.github.dino060311.cutscene;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CutsceneCommand implements CommandExecutor {
    
    private final JavaPlugin plugin;

    public CutsceneCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        // 1. 플레이어 얼리기 (Listener의 명단에 추가)
        CutsceneListener.frozenPlayers.add(player.getUniqueId());

        // 2. 극적인 타이틀과 사운드 연출
        // sendTitle(큰 제목, 작은 제목, 나타나는 시간, 유지 시간, 사라지는 시간)
        player.sendTitle("§4§l[ 범인 발견! ]", "§c절대 움직이지 마라!", 10, 60, 10);
        
        // 천둥 번개 소리와 엔더드래곤 울음소리를 섞어 긴장감 극대화!
        player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 0.5f);

        // 3. 3초(60틱) 뒤에 다시 움직일 수 있게 풀어주기 (스케줄러 활용)
        new BukkitRunnable() {
            @Override
            public void run() {
                // 명단에서 빼주면 다시 움직일 수 있습니다.
                CutsceneListener.frozenPlayers.remove(player.getUniqueId());
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            }
        }.runTaskLater(plugin, 60L); // 60L = 3초 뒤에 내부의 run() 실행

        return true;
    }
}