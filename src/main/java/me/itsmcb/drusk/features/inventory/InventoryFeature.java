package me.itsmcb.drusk.features.inventory;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class InventoryFeature extends BukkitFeature {

    public InventoryFeature(Drusk instance) {
        super("Inventory", "Inventory-related actions", null, instance);
        registerCommand(new InventoryCommand(instance));
    }
}
