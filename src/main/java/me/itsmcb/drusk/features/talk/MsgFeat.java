package me.itsmcb.drusk.features.talk;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class MsgFeat extends BukkitFeature {
    public MsgFeat(Drusk instance) {
        super("Talk", "Talk with others", null, instance);
        registerCommand(new MsgCmd(instance));
        registerCommand(new SayCmd(instance));
    }
}
