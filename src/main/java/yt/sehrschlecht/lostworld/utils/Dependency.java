package yt.sehrschlecht.lostworld.utils;

import org.bukkit.Bukkit;

public enum Dependency {
    PROTOCOLLIB("ProtocolLib");

    private final String name;

    Dependency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return (Bukkit.getPluginManager().getPlugin(name) != null)
                && Bukkit.getPluginManager().getPlugin(name).isEnabled();
    }
}
