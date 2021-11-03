package yt.sehrschlecht.lostworld.packetlisteners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import org.bukkit.plugin.Plugin;
import yt.sehrschlecht.lostworld.config.Config;

import java.util.Collections;

public class ServerListListeners extends PacketAdapter {

    public ServerListListeners(Plugin plugin) {
        super(plugin, PacketType.Status.Server.SERVER_INFO);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Config config = Config.getInstance();
        if(!config.isServerListEnabled()) return;
        WrappedServerPing ping = event.getPacket().getServerPings().read(0);
        ping.setPlayersOnline(config.getServerListOnlinePlayers());
        ping.setPlayers(Collections.emptyList());
        ping.setBukkitPlayers(Collections.emptyList());
        if(config.isVersionNameEnabled()) {
            ping.setVersionName(config.getVersionNameMessage().replace("&", "§"));
            ping.setVersionProtocol(-1);
        }
    }

    public void init() {
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }
}
