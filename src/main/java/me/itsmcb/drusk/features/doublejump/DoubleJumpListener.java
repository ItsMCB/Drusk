package me.itsmcb.drusk.features.doublejump;

import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpListener implements Listener {

    private DoubleJumpFeature feature;

    public DoubleJumpListener(DoubleJumpFeature feature) {
        this.feature = feature;
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
        // TODO check if enabled
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        event.setCancelled(true);
        player.setFlying(false);
        if (feature.getDJCooldownManager().isInCooldown(player.getUniqueId())) {
            player.sendActionBar(new BukkitMsgBuilder("Cooldown active").get());
            return;
        }
        player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(0.8));
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, -1);
        feature.getDJCooldownManager().activateCooldown(player.getUniqueId());
    }

}
