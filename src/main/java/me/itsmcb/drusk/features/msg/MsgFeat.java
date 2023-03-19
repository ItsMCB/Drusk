package me.itsmcb.drusk.features.msg;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class MsgFeat extends BukkitFeature {
    public MsgFeat(Drusk instance) {
        super("Msg", "Message other players", null, instance);
        registerCommand(new MsgCmd(instance));
    }
}
