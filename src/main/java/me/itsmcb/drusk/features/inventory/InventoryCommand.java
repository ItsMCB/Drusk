package me.itsmcb.drusk.features.inventory;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;

public class InventoryCommand extends CustomCommand {
    private final Drusk instance;
    public InventoryCommand(Drusk instance) {
        super("inventory", "", "drusk.admin");
        this.instance = instance;
        registerSubCommand(new Clear());
    }
}
