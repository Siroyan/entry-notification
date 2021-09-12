package net.siroyan.dev.mc.plugin.entrynotification;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EntryEvent implements Listener {

    private DiscordGateway dg;

    public EntryEvent(DiscordGateway dg) {
        this.dg = dg;
    }

    public void onEntry(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try {
            dg.postJoinMessage(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
