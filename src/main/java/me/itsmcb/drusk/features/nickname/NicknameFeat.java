package me.itsmcb.drusk.features.nickname;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class NicknameFeat extends BukkitFeature {
    public NicknameFeat(Drusk instance) {
        super("Nickname", "", null, instance);
        registerCommand(new NicknameCmd(instance));
    }
}
