package me.itsmcb.drusk.features.firework;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class FireworkFeat extends BukkitFeature {
    public FireworkFeat(Drusk instance) {
        super("Firework", "Firework creation system", null, instance);
        registerCommand(new FireworkCmd(instance));
    }
}
