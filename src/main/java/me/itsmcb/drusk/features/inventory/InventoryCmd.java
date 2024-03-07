package me.itsmcb.drusk.features.inventory;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;

public class InventoryCmd extends CustomCommand {
    private final Drusk instance;
    public InventoryCmd(Drusk instance) {
        super("inventory", "Manage a player's inventory.", "drusk.admin");
        this.instance = instance;
        registerSubCommand(new ClearSCmd());
        registerSubCommand(new OpenSCmd());
    }
}
