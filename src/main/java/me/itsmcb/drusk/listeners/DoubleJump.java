package me.itsmcb.drusk.listeners;

import me.itsmcb.drusk.Drusk;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJump implements Listener {

    private Drusk instance;

    public DoubleJump (Drusk instance) {
        this.instance = instance;
    }

    // Allow flight so players can trigger double jump
    @EventHandler
    public void setFlight(PlayerJoinEvent event) {
        event.getPlayer().setAllowFlight(true);
        event.getPlayer().setFlying(false);
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        event.setCancelled(true);
        player.setFlying(false);
        if (instance.getDJCooldownManager().isInCooldown(player.getUniqueId())) {
            return;
        }
        player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(0.8));
        player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
        instance.getDJCooldownManager().activateCooldown(player.getUniqueId());
    }

}
