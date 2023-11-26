package me.itsmcb.drusk.features.weext;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class WorldEditExtensionsFeat extends BukkitFeature {

    public WorldEditExtensionsFeat(Drusk instance) {
        super("World Edit Extensions", "World Edit extension", null, instance);
        registerCommand(new WCmd());
        registerCommand(new PosCmd(1));
        registerCommand(new PosCmd(2));
        registerCommand(new WorldEditMenuCmd(instance));
    }
}
