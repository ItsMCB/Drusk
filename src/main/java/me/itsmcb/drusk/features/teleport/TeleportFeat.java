package me.itsmcb.drusk.features.teleport;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.drusk.features.teleport.tpto.TpToCmd;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class TeleportFeat extends BukkitFeature {
    public TeleportFeat(Drusk instance) {
        super("Teleport Feature", "", null, instance);
        registerCommand(new TpToCmd(instance));
        registerCommand(new TpHereCmd());
        registerCommand(new TpmCmd(instance));
        registerCommand(new TopCmd());
    }
}
