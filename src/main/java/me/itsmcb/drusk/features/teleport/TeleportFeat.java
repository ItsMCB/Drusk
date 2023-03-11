package me.itsmcb.drusk.features.teleport;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class TeleportFeat extends BukkitFeature {
    public TeleportFeat(Drusk instance) {
        super("Teleport Feature", "", null, instance);
        registerCommand(new TpToCmd());
        registerCommand(new TpHereCmd());
    }
}
