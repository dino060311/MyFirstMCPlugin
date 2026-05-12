package io.github.dino060311.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class MerchantNpcListener implements Listener {

    @EventHandler
    public void onMerchantInteract(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        
        if (entity instanceof Villager && entity.getCustomName() != null && entity.getCustomName().equals("§e무기 대장장이")) {
            event.setCancelled(true);
            
            openVillagerTradingGui(event.getPlayer());
        }
    }

    private void openVillagerTradingGui(Player player) {
        // 커스텀 상인 객체 생성 (창 이름 설정)
        Merchant merchant = Bukkit.createMerchant("§8무기 대장장이");

        // 거래 목록을 담을 리스트 생성
        List<MerchantRecipe> recipes = new ArrayList<>();
        
        //에메랄드 7개 -> 다이아몬드 검 1개
        MerchantRecipe recipe1 = new MerchantRecipe(new ItemStack(Material.DIAMOND_SWORD, 1), 999);
        recipe1.addIngredient(new ItemStack(Material.EMERALD, 7)); // 필요한 재료
        recipes.add(recipe1);

        //에메랄드 7개 -> 다이아몬드 도끼 1개
        MerchantRecipe recipe2 = new MerchantRecipe(new ItemStack(Material.DIAMOND_AXE, 1), 999);
        recipe2.addIngredient(new ItemStack(Material.EMERALD, 7));
        recipes.add(recipe2);
        
        // 에메랄드 3개 -> 활 1개
        MerchantRecipe recipe3 = new MerchantRecipe(new ItemStack(Material.BOW, 1), 999); 
        recipe3.addIngredient(new ItemStack(Material.EMERALD, 3));
        recipes.add(recipe3);

        // 에메랄드 1개 -> 화살 16개
        MerchantRecipe recipe4 = new MerchantRecipe(new ItemStack(Material.ARROW, 16), 999); 
        recipe4.addIngredient(new ItemStack(Material.EMERALD, 1));
        recipes.add(recipe4);

        // 3. 상인에게 방금 만든 거래 목록 적용
        merchant.setRecipes(recipes);

        // 4. 플레이어에게 거래창 열어주기
        player.openMerchant(merchant, true);
    }
}