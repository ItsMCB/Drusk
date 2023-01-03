package me.itsmcb.drusk.features.npctool;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class NPCToolFeature extends BukkitFeature {

    private Drusk instance;

    public NPCToolFeature(Drusk instance) {
        super("NPC Tool", "Tool for creating NPCs with Citizens", null, instance);
        this.instance = instance;
        registerCommand(new NPCToolCMD(instance));
    }
}
