package yt.sehrschlecht.lostworld.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;
import yt.sehrschlecht.lostworld.LostWorld;
import yt.sehrschlecht.lostworld.config.Config;

public class PlayerListeners implements Listener {
    private static Team hideTeam;

    static {
        hideTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("hideTeam");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Config config = Config.getInstance();
        if(config.shouldHideNearPlayersNametags()) {
            if(!hideTeam.hasEntry(player.getName())) {
                hideTeam.addEntry(player.getName());
            }
        } else if(hideTeam.hasEntry(player.getName())) {
            hideTeam.removeEntry(player.getName());
        }
        if(!Config.getInstance().shouldHidePlayers()) return;
        for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
            if(currentPlayer != player) {
                player.hidePlayer(LostWorld.getPlugin(), currentPlayer);
                currentPlayer.hidePlayer(LostWorld.getPlugin(), player);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Config config = Config.getInstance();
        if(!config.shouldDisableChat()) return;
        Player player = event.getPlayer();
        if(!config.getChatDisabledMessage().isEmpty() && player.hasPermission(config.getChatBypassPermission())) return;
        event.setCancelled(true);
        if(!config.getChatDisabledMessage().isEmpty()) {
            player.sendMessage(config.getChatDisabledMessage().replace("&", "§"));
        }
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Config config = Config.getInstance();
        if(!config.getCommandBypassPermission().isEmpty() && event.getPlayer().hasPermission(config.getCommandBypassPermission())) return;
        if(Config.getInstance().shouldDisableCommandOutput()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent event) {
        Config config = Config.getInstance();
        if(!config.getCommandBypassPermission().isEmpty() && event.getPlayer().hasPermission(config.getCommandBypassPermission())) return;
        if(Config.getInstance().shouldDisableCommandOutput()) {
            event.getCommands().clear();
        }
    }
}
