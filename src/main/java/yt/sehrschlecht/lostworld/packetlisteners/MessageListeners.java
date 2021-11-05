package yt.sehrschlecht.lostworld.packetlisteners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class MessageListeners extends PacketAdapter {
    private static List<PacketType> typesToListen = Arrays.asList(
            PacketType.Play.Server.SET_ACTION_BAR_TEXT,

            PacketType.Play.Server.SET_TITLE_TEXT,
            PacketType.Play.Server.SET_SUBTITLE_TEXT,
            PacketType.Play.Server.SET_TITLES_ANIMATION,
            PacketType.Play.Server.CLEAR_TITLES,

            PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE,
            PacketType.Play.Server.SCOREBOARD_OBJECTIVE,
            PacketType.Play.Server.SCOREBOARD_SCORE
    );

    public MessageListeners(Plugin plugin) {
        super(plugin, typesToListen);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if(typesToListen.contains(event.getPacketType())) {
            event.setCancelled(true);
        }
    }

    public void init() {
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }
}
