package me.itsmcb.drusk.features.book;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class BookFeat extends BukkitFeature {
    public BookFeat(Drusk instance) {
        super("Book", "Open books from configurations", null, instance);
        registerCommand(new OpenBookCmd(instance));
    }
}
