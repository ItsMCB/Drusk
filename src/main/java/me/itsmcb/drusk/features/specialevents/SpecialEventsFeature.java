package me.itsmcb.drusk.features.specialevents;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class SpecialEventsFeature extends BukkitFeature {

    public SpecialEventsFeature(Drusk instance) {
        super("Welcome", "Welcome new players to your server", null, instance);
        registerListener(new SpecialEventsListener(instance));
    }
}
