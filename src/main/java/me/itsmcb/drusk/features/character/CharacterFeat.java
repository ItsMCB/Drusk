package me.itsmcb.drusk.features.character;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class CharacterFeat extends BukkitFeature {
    public CharacterFeat(Drusk instance) {
        super("Character", "", null, instance);
        registerCommand(new CharacterCmd(instance));
    }
}
