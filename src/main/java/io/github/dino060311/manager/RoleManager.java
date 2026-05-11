package io.github.dino060311.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoleManager {

    private final Map<UUID, String> playerRoles = new HashMap<>();

    public void setRole(Player player, String role) {
        playerRoles.put(player.getUniqueId(), role);
    }

    public void removeRole(Player player) {
        playerRoles.remove(player.getUniqueId());
    }

    public String getRole(Player player) {
        return playerRoles.get(player.getUniqueId());
    }

    public void clearRoles() {
        playerRoles.clear();
    }
}