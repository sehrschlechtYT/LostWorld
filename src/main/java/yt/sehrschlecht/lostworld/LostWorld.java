package yt.sehrschlecht.lostworld;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import yt.sehrschlecht.lostworld.commands.LostWorldCommand;
import yt.sehrschlecht.lostworld.config.Config;
import yt.sehrschlecht.lostworld.listeners.PlayerListeners;
import yt.sehrschlecht.lostworld.packetlisteners.ServerListListeners;
import yt.sehrschlecht.lostworld.services.PlayerHideService;
import yt.sehrschlecht.lostworld.utils.Dependency;

import java.util.logging.Level;

public final class LostWorld extends JavaPlugin {
    private static LostWorld plugin;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        getCommand("lostworld").setExecutor(new LostWorldCommand());
        getCommand("lostworld").setTabCompleter(new LostWorldCommand());

        if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("hideTeam") == null) {
            Team team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("hideTeam");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        }

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListeners(), this);

        new PlayerHideService(this).start();

        if(Config.getInstance().isServerListEnabled()) {
            if(Dependency.PROTOCOLLIB.isAvailable()) {
                new ServerListListeners(this).init();
            } else {
                getLogger().log(Level.SEVERE, "You have to install " + Dependency.PROTOCOLLIB.getName() + " to use the server list feature!");
            }
        }
    }

    @Override
    public void onDisable() {

    }

    public static LostWorld getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        return "§7[§bLostWorld§7] ";
    }
}
