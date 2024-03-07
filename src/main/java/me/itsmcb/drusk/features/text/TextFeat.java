package me.itsmcb.drusk.features.text;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class TextFeat extends BukkitFeature {
    public TextFeat(Drusk instance) {
        super("Book", "Open books from configurations", null, instance);
        registerCommand(new OpenTextCmd(instance));
    }
}
