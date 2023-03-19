package me.itsmcb.drusk.features.fun;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class FunFeat extends BukkitFeature {
    public FunFeat(Drusk instance) {
        super("Fun", "Fun commands", null, instance);
        registerCommand(new SmiteCmd(instance));
    }
}
