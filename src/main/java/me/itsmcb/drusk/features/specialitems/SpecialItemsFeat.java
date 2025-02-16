package me.itsmcb.drusk.features.specialitems;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

public class SpecialItemsFeat extends BukkitFeature {

    public static NamespacedKey specialItemPlacerKey = new NamespacedKey(Bukkit.getPluginManager().getPlugin("Drusk"), "special-item-placer");

    public SpecialItemsFeat(Drusk instance) {
        super("Special Items", "Obtain special items", null, instance);
        registerCommand(new SpecialItemsCmd(instance, specialItemPlacerKey));
        registerListener(new SpecialItemsListener(instance, specialItemPlacerKey));
    }
}
