package me.itsmcb.drusk.features.head;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class HeadFeat extends BukkitFeature {

    public HeadFeat(Drusk instance) {
        super("Heads", "", null, instance);
        registerCommand(new HeadCmd(instance));
    }
}
