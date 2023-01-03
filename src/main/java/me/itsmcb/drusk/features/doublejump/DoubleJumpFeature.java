package me.itsmcb.drusk.features.doublejump;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;
import me.itsmcb.vexelcore.common.api.manager.CooldownManager;
import org.bukkit.Bukkit;

public class DoubleJumpFeature extends BukkitFeature {

    private CooldownManager DJCooldownManager;

    public CooldownManager getDJCooldownManager() {
        return DJCooldownManager;
    }

    public DoubleJumpFeature(Drusk instance) {
        super("DoubleJump", "Double jump", null, instance);
        this.DJCooldownManager = new CooldownManager(2);
        registerListener(new DoubleJumpListener(this));
    }

    @Override
    public void enableTriggers() {
        Bukkit.getOnlinePlayers().forEach(player -> player.setAllowFlight(true));
        Bukkit.getOnlinePlayers().forEach(player -> player.setFlying(false));
    }

    @Override
    public void disableTriggers() {
        Bukkit.getOnlinePlayers().forEach(player -> player.setAllowFlight(false));
        Bukkit.getOnlinePlayers().forEach(player -> player.setFlying(false));
    }
}
