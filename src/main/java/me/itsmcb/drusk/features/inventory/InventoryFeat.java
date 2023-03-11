package me.itsmcb.drusk.features.inventory;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class InventoryFeat extends BukkitFeature {

    public InventoryFeat(Drusk instance) {
        super("Inventory", "Inventory-related actions", null, instance);
        registerCommand(new InventoryCmd(instance));
    }
}
