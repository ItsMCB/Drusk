package me.itsmcb.drusk.features.item;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class ItemFeat extends BukkitFeature {

    public ItemFeat(Drusk instance) {
        super("Item", "Manipulate items", null, instance);
        registerCommand(new ItemCmd(instance));
    }
}
