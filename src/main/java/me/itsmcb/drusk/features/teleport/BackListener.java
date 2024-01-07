package me.itsmcb.drusk.features.teleport;

import me.itsmcb.drusk.Drusk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BackListener implements Listener {

    private Drusk instance;

    public BackListener(Drusk instance) {
        this.instance = instance;
    }

    @EventHandler
    public void teleportEvent(PlayerTeleportEvent e) {
        instance.getLastTeleportLocation().put(e.getPlayer().getUniqueId(),e.getPlayer().getLocation());
    }
}
