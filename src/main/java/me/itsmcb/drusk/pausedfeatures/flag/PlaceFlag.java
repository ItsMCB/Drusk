package me.itsmcb.drusk.pausedfeatures.flag;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class PlaceFlag extends BukkitFeature {

    private Drusk instance;

    public PlaceFlag(Drusk instance) {
        super("Place Flag","Place flags WIP",null, instance);
        this.instance = instance;
        registerListener(new PlaceFlagListener());
    }
}
