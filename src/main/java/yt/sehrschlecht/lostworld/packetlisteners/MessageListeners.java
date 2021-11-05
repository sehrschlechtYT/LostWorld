package yt.sehrschlecht.lostworld.packetlisteners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
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
            PacketType.Play.Server.SCOREBOARD_SCORE,

            PacketType.Play.Server.CHAT,

            PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER
    );

    public MessageListeners(Plugin plugin) {
        super(plugin, typesToListen);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if(typesToListen.contains(event.getPacketType())) {
            if(event.getPacketType().equals(PacketType.Play.Server.CHAT)) {
                WrappedChatComponent wrappedComponent = event.getPacket().getChatComponents().read(0);
                if(wrappedComponent == null) return;
                BaseComponent[] components = ComponentSerializer.parse(wrappedComponent.getJson());
                TextComponent textComponent = new TextComponent(components);
                String message = textComponent.getText();
                if(message.contains("<") && message.contains(">")) {
                    return;
                }
            }
            event.setCancelled(true);
        }
    }

    public void init() {
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }
}
