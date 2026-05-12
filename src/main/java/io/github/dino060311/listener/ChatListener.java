package io.github.dino060311.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ChatListener implements Listener {

    private Plugin plugin;

    public ChatListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // 플레이어가 친 채팅 메시지 가져오기
        String message = event.getMessage();
        Player player = event.getPlayer();

        // 메시지에 "안녕"이 포함되어 있는지 확인
        if (message.contains("안녕")) {
            // 서버가 해당 플레이어에게 메시지 보내기
            player.sendMessage("§e[System] §f반갑습니다. 서버 플러그인이 정상 작동 중입니다.");
        }

        if (message.contains("다이아몬드")) {
            // 인벤토리를 건드리는 작업은 반드시 runTask 안에서 해야 에러가 안남.
            Bukkit.getScheduler().runTask(plugin, () -> {
                // 다이아몬드 1개 생성
                ItemStack diamond = new ItemStack(Material.DIAMOND, 1);

                // 플레이어의 인벤토리에 추가
                player.getInventory().addItem(diamond);

                // 성공 메시지 전송
                player.sendMessage("§b[System] §f인벤토리에 다이아몬드를 지급했습니다!");
            });
        }
    }

    @EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // 1. 플레이어가 들고 있는 아이템이 '막대기(STICK)'인지 확인
        if (player.getInventory().getItemInMainHand().getType() == Material.STICK) {

            // 2. 우클릭(공중 혹은 블록)을 했는지 확인
            if (event.getAction().name().contains("RIGHT_CLICK")) {

                // 3. 플레이어가 바라보고 있는 위치(최대 100칸)를 가져옴
                org.bukkit.block.Block targetBlock = player.getTargetBlockExact(100);

                if (targetBlock != null) {
                    // 4. 해당 위치에 번개를 소환!
                    player.getWorld().strikeLightning(targetBlock.getLocation());

                    // 5. 효과음과 메시지 추가 (선택사항이지만 넣으면 훨씬 멋져요!)
                    player.sendMessage("§6[Magic] §f번개 지팡이를 사용했습니다!");
                }
            }
        }
    }
}