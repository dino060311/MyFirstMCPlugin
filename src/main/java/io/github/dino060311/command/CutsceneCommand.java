package io.github.dino060311.command;

import io.github.dino060311.listener.CutsceneListener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CutsceneCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public CutsceneCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // 1. 뒤에 인자(args)가 있을 때 (예: /컷신 @a, /컷신 닉네임)
        if (args.length > 0) {
            List<Entity> targets = Bukkit.selectEntities(sender, args[0]);
            for (Entity entity : targets) {
                if (entity instanceof Player targetPlayer) {
                    playCutscene(targetPlayer);
                }
            }
        }
        // 2. 뒤에 아무것도 없을 때 (예: /컷신) -> 명령어를 친 본인에게 실행
        else {
            if (sender instanceof Player player) {
                playCutscene(player);
            }
        }

        return true;
    }

    private void playCutscene(final Player targetPlayer) {

        // 1. 플레이어 얼리기 (Listener의 명단에 추가)
        CutsceneListener.frozenPlayers.add(targetPlayer.getUniqueId());

        // 2. 극적인 타이틀과 사운드 연출
        // sendTitle(큰 제목, 작은 제목, 나타나는 시간, 유지 시간, 사라지는 시간)
        targetPlayer.sendTitle("§4§l[ 범인 발견! ]", "§c절대 움직이지 마라!", 10, 60, 10);

        // 천둥 번개 소리와 엔더드래곤 울음소리를 섞어 긴장감 극대화!
        targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
        targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 0.5f);

        // 3. 3초(60틱) 뒤에 다시 움직일 수 있게 풀어주기 (스케줄러 활용)
        new BukkitRunnable() {
            @Override
            public void run() {
                // 명단에서 빼주면 다시 움직일 수 있습니다.
                CutsceneListener.frozenPlayers.remove(targetPlayer.getUniqueId());
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            }
        }.runTaskLater(plugin, 60L); // 60L = 3초 뒤에 내부의 run() 실행
    }
}