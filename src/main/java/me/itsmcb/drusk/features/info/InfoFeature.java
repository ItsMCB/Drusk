package me.itsmcb.drusk.features.info;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class InfoFeature extends BukkitFeature {

    public InfoFeature(Drusk instance) {
        super("Info", "Manage various aspects of the server", null, instance);
        registerCommand(new InfoCMD(instance));
    }
}
