package me.itsmcb.drusk.features.spawn;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class SpawnFeature extends BukkitFeature {

    public SpawnFeature(Drusk instance) {
        super("spawn", "Handles spawn command and event", "spawn", instance);
        registerCommand(new SpawnCMD(instance));
    }
}
