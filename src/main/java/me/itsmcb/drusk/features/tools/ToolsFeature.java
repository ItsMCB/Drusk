package me.itsmcb.drusk.features.tools;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class ToolsFeature extends BukkitFeature {

    private Drusk instance;

    public ToolsFeature(Drusk instance) {
        super("Tools", "Useful server tools", null, instance);
        this.instance = instance;
        registerCommand(new NPCToolCMD(instance));
        registerCommand(new ColorsCmd());
    }
}
