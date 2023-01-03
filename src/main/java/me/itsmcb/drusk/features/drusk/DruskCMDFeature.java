package me.itsmcb.drusk.features.drusk;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class DruskCMDFeature extends BukkitFeature {

    private Drusk instance;

    public DruskCMDFeature(Drusk instance) {
        super("Drusk", "Manage Drusk", null, instance);
        this.instance = instance;
        registerCommand(new DruskCMD(instance));
    }
}
