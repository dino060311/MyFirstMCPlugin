package io.github.dino060311.service;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BombService {

    private final Plugin plugin;

    public BombService(Plugin plugin) {
        this.plugin = plugin;
    }

    public void throwBomb(Player player, ItemStack item) {

        // TNT 1개 소모
        item.setAmount(item.getAmount() - 1);

        // 플레이어 눈 위치에 TNT 아이템을 생성하여 던짐
        Item thrownBomb = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.TNT));

        // 플레이어가 바라보는 방향으로 날려보냄
        thrownBomb.setVelocity(player.getLocation().getDirection().multiply(1.5));

        // 던진 폭탄을 다시 주울 수 없게 설정
        thrownBomb.setPickupDelay(99999);

        // 4. 타이머 시작 (3초 뒤 터짐)
        new BukkitRunnable() {
            int count = 3;

            @Override
            public void run() {

                if (count > 0) {
                    // 폭탄이 날아가는 동안 폭탄 위치에서 소리 재생
                    thrownBomb.getWorld().playSound(thrownBomb.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1.0f, 1.0f);
                    count--;
                } else {
                    // 0초가 되면 폭발!
                    thrownBomb.getWorld().createExplosion(thrownBomb.getLocation(), 4.0F, false, false);

                    // 던져진 TNT 아이템 형체 삭제
                    thrownBomb.remove();

                    // 타이머 종료
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // 0틱 후 시작, 매 20틱(1초)마다 반복
    }
}