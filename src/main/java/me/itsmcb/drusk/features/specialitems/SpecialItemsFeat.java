package me.itsmcb.drusk.features.specialitems;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;
import org.bukkit.NamespacedKey;

public class SpecialItemsFeat extends BukkitFeature {

    public NamespacedKey specialItemPlacerKey;

    public SpecialItemsFeat(Drusk instance) {
        super("Special Items", "Obtain special items", null, instance);
        specialItemPlacerKey = new NamespacedKey(instance, "special-item-placer");
        registerCommand(new SpecialItemsCmd(instance, specialItemPlacerKey));
        registerListener(new SpecialItemsListener(specialItemPlacerKey));
    }
}
