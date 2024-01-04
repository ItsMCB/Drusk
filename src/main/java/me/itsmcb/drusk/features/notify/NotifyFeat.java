package me.itsmcb.drusk.features.notify;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class NotifyFeat extends BukkitFeature {
    public NotifyFeat(Drusk instance) {
        super("Notify", "Get notified about events", null, instance);
        registerListener(new NotifySounds());
    }
}
