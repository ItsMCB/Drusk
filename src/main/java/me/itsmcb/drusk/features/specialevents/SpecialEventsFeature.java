package me.itsmcb.drusk.features.specialevents;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpecialEventsFeature extends BukkitFeature {

    public SpecialEventsFeature(Drusk instance) {
        super("Welcome", "Welcome new players to your server", null, instance);
        registerListener(new SpecialEventsListener(instance));
    }
}
