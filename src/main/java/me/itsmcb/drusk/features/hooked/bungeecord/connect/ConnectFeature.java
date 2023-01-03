package me.itsmcb.drusk.features.hooked.bungeecord.connect;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class ConnectFeature extends BukkitFeature {

    private Drusk instance;

    public ConnectFeature(Drusk instance) {
        super("Connect", "Switch to another server via plugin message request", null, instance);
        this.instance = instance;
        instance.getServer().getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
        registerCommand(new ConnectCMD(instance));
    }
}
