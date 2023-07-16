package me.itsmcb.drusk.features.item;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.drusk.features.item.lore.ItemLore;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;

public class ItemCmd extends CustomCommand {
    private final Drusk instance;
    public ItemCmd(Drusk instance) {
        super("item", "Display or edit item in hand.", "drusk.item.edit");
        this.instance = instance;
        registerSubCommand(new ItemInfoScmd(instance));
        registerSubCommand(new ItemModelData(instance));
        registerSubCommand(new ItemRename(instance));
        registerSubCommand(new ItemLore(instance));
    }
}
