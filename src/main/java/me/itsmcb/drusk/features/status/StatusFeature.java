package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class StatusFeature extends BukkitFeature {

    public StatusFeature(Drusk instance) {
        super("Status", "View the status of various server aspects", null, instance);
        registerCommand(new StatusCMD(instance));
    }
}
