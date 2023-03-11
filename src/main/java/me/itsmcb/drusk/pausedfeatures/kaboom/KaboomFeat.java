package me.itsmcb.drusk.pausedfeatures.kaboom;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class KaboomFeat extends BukkitFeature {
    public KaboomFeat(Drusk instance) {
        super("Kaboom", "BOOM", null, instance);
        registerCommand(new KaboomCmd());
    }
}
