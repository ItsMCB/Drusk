package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class SkinFeature extends BukkitFeature {

    public SkinFeature(Drusk instance) {
        super("skin", "Change skin on demand", null, instance);
        registerCommand(new SkinCMD(instance));
    }
}
