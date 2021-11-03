package yt.sehrschlecht.lostworld.config;

import org.bukkit.configuration.file.FileConfiguration;
import yt.sehrschlecht.lostworld.LostWorld;

public class Config {
    private static Config config = null;

    private final boolean hidePlayersEnabled;
    private final boolean showNearPlayersEnabled;
    private final int showNearPlayersDistance;
    private final boolean hideNearPlayersNametags;

    private final boolean disableChat;
    private final String chatBypassPermission;
    private final String chatDisabledMessage;

    private final boolean serverListEnabled;
    private final int serverListOnlinePlayers;
    private final boolean versionNameEnabled;
    private final String versionNameMessage;


    public Config(boolean hidePlayersEnabled, boolean showNearPlayersEnabled, int showNearPlayersDistance, boolean hideNearPlayersNametags, boolean disableChat, String chatBypassPermission, String chatDisabledMessage, boolean serverListEnabled, int serverListOnlinePlayers, boolean versionNameEnabled, String versionNameMessage) {
        this.hidePlayersEnabled = hidePlayersEnabled;
        this.showNearPlayersEnabled = showNearPlayersEnabled;
        this.showNearPlayersDistance = showNearPlayersDistance;
        this.hideNearPlayersNametags = hideNearPlayersNametags;
        this.disableChat = disableChat;
        this.chatBypassPermission = chatBypassPermission;
        this.chatDisabledMessage = chatDisabledMessage;
        this.serverListEnabled = serverListEnabled;
        this.serverListOnlinePlayers = serverListOnlinePlayers;
        this.versionNameEnabled = versionNameEnabled;
        this.versionNameMessage = versionNameMessage;
        config = this;
    }

    public static Config getInstance() {
        if(config == null) {
            reload();
        }
        return config;
    }

    public static void reload() {
        FileConfiguration configuration = LostWorld.getPlugin().getConfig();
        config = new Config(
                configuration.getBoolean("hide-players.enabled"),
                configuration.getBoolean("hide-players.show-near-players.enabled"),
                configuration.getInt("hide-players.show-near-players.distance"),

                configuration.getBoolean("hide-players.show-near-players.hideNametags"),
                configuration.getBoolean("chat.disable-chat"),
                configuration.getString("chat.bypass-permission"),
                configuration.getString("chat.disabled-message"),

                configuration.getBoolean("server-list.enabled"),
                configuration.getInt("server-list.online-players"),
                configuration.getBoolean("server-list.version-name.enabled"),
                configuration.getString("server-list.version-name.message"));
    }

    public boolean shouldHidePlayers() {
        return hidePlayersEnabled;
    }

    public boolean shouldShowNearPlayers() {
        return showNearPlayersEnabled;
    }

    public int getNearPlayersDistance() {
        return showNearPlayersDistance;
    }

    public boolean shouldHideNearPlayersNametags() {
        return hideNearPlayersNametags;
    }

    public boolean shouldDisableChat() {
        return disableChat;
    }

    public String getChatBypassPermission() {
        return chatBypassPermission;
    }

    public String getChatDisabledMessage() {
        return chatDisabledMessage;
    }

    public boolean isServerListEnabled() {
        return serverListEnabled;
    }

    public int getServerListOnlinePlayers() {
        return serverListOnlinePlayers;
    }

    public boolean isVersionNameEnabled() {
        return versionNameEnabled;
    }

    public String getVersionNameMessage() {
        return versionNameMessage;
    }
}
