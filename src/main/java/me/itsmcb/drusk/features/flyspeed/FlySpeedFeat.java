package me.itsmcb.drusk.features.flyspeed;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class FlySpeedFeat extends BukkitFeature {

    public FlySpeedFeat(Drusk instance) {
        super("flyspeed", "Manage flight speed of self and others", null, instance);
        registerCommand(new FlySpeedCmd(instance, "flyspeed"));
        registerCommand(new FlySpeedCmd(instance, "fs"));
    }
}
