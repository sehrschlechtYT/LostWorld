package yt.sehrschlecht.lostworld.services;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.lostworld.config.Config;

public class PlayerHideService {
    private final JavaPlugin plugin;

    public PlayerHideService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            Config config = Config.getInstance();
            if(!config.shouldShowNearPlayers()) return;
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                    if(player.getWorld().equals(currentPlayer.getWorld()) && player.getLocation().distance(currentPlayer.getLocation()) < config.getNearPlayersDistance()) {
                        if (!player.canSee(currentPlayer)) {
                            player.showPlayer(plugin, currentPlayer);
                        }
                    } else {
                        if (player.canSee(currentPlayer)) {
                            player.hidePlayer(plugin, currentPlayer);
                        }
                    }
                }
            }
        }, 0L, 5L);
    }
}
