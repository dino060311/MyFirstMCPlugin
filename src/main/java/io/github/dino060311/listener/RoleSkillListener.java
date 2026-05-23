package io.github.dino060311.listener;

import io.github.dino060311.manager.RoleManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoleSkillListener implements Listener {

    private final RoleManager roleManager;

    // 경찰 스킬 쿨타임 저장
    private final Map<UUID, Long> copCooldowns = new HashMap<>();

    private static final int COP_COOLDOWN_SECONDS = 15;

    public RoleSkillListener(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();

        // 플레이어 우클릭이 아니면 무시
        if (!(event.getRightClicked() instanceof Player target))
            return;

        String role = roleManager.getRole(player);

        if (role == null)
            return;

        // ==========================
        // 경찰 능력
        // ==========================
        if (role.equals("경찰")) {

            if (player.getInventory().getItemInMainHand().getType() != Material.COMPASS)
                return;

            UUID playerId = player.getUniqueId();
            long currentTime = System.currentTimeMillis();

            // 쿨타임 검사
            if (copCooldowns.containsKey(playerId)) {
                long lastUseTime = copCooldowns.get(playerId);
                long timePassed = (currentTime - lastUseTime) / 1000;

                if (timePassed < COP_COOLDOWN_SECONDS) {
                    long leftTime = COP_COOLDOWN_SECONDS - timePassed;

                    player.sendMessage("§9[경찰] §f스캐너 충전 중입니다! 남은 시간: §e" + leftTime + "초");
                    player.playSound(player.getLocation(),
                            Sound.BLOCK_DISPENSER_FAIL, 1.0f, 1.0f);
                    return;
                }
            }

            // 쿨타임 적용
            copCooldowns.put(playerId, currentTime);

            String targetRole = roleManager.getRole(target);

            if (targetRole == null) {
                targetRole = "시민";
            }

            player.sendMessage("§9[경찰 스캐너] §f삐빅- §e"
                    + target.getName()
                    + "§f님의 직업은 §c"
                    + targetRole
                    + "§f입니다!");

            player.playSound(player.getLocation(),
                    Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                    1.0f,
                    2.0f);
        }

        // ==========================
        // 마피아 능력
        // ==========================
        else if (role.equals("마피아")) {

            World world = player.getWorld();
            long time = world.getTime();

            // 밤 시간 체크 (13000 ~ 23000)
            if (time < 13000 || time > 23000) {
                player.sendMessage("§c[마피아] §f암살은 §c밤§f에만 가능합니다.");
                return;
            }

            if (player.getInventory().getItemInMainHand().getType() != Material.NETHERITE_SWORD) {
                player.sendMessage("§c[마피아] §f네더라이트 검을 들어야 암살할 수 있습니다.");
                return;
            }

            // 뒤 판정 (Yaw 비교)
            float playerYaw = player.getLocation().getYaw();
            float targetYaw = target.getLocation().getYaw();

            float diff = Math.abs(playerYaw - targetYaw);

            if (diff > 180) {
                diff = 360 - diff;
            }

            // 같은 방향 보고 있으면 뒤에서 접근 성공
            if (diff < 45) {

                target.setHealth(0);

                player.sendMessage("§4[암살] §c어둠 속에서 "
                        + target.getName()
                        + "님을 처치했습니다...");

                player.playSound(player.getLocation(),
                        Sound.ENTITY_PLAYER_ATTACK_CRIT,
                        1.0f,
                        0.5f);

                Component mafiaBroadcast = Component.text("[", NamedTextColor.DARK_GRAY)
                        .append(Component.text("!", NamedTextColor.RED))
                        .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                        .append(Component.text("누군가 밤의 그림자 속에서 차갑게 식어버렸습니다...", NamedTextColor.RED));

                player.getServer().broadcast(mafiaBroadcast);

            } else {
                player.sendMessage(
                        "§c[마피아] §f대상의 완벽한 §c등 뒤§f에서만 암살할 수 있습니다.");
            }
        }
    }
}