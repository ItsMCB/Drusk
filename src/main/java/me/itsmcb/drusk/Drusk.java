package me.itsmcb.drusk;

import me.itsmcb.drusk.commands.hooked.AboutCMD;
import me.itsmcb.drusk.commands.hooked.bungeecord.ConnectCMD;
import me.itsmcb.drusk.listeners.DoubleJump;
import me.itsmcb.vexelcore.common.api.manager.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Drusk extends JavaPlugin {

    private Drusk instance;
    private CooldownManager DJCooldownManager;

    public CooldownManager getDJCooldownManager() {
        return DJCooldownManager;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.instance = this;
        loadFeatures();
        getCommand("connect").setExecutor(new ConnectCMD(instance));
        getCommand("connect").setTabCompleter(new ConnectCMD(instance));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getCommand("abt").setExecutor(new AboutCMD(instance));
    }

    // TODO VexelCore "Feature" API to quickly load/unload events, commands, channel messages, etc.
    // TODO finish this so config settings work
    private void loadFeatures() {
        if (getConfig().getBoolean("features.double-jump.enabled")) {
            this.DJCooldownManager = new CooldownManager((long) getConfig().getDouble("features.double-jump.cooldown"));
            Bukkit.getPluginManager().registerEvents(new DoubleJump(instance), this);
        }
    }

    private void unloadFeatures() {
        // TODO for a "/drusk reload" command (wait for VC Feature API tho)
    }
}
