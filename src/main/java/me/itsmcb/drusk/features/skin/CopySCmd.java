package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;

public class CopySCmd extends CustomCommand {

    private Drusk instance;

    public CopySCmd(Drusk instance) {
        super("copy", "Copy a skin from a source", "drusk.skin");
        this.instance = instance;
        registerSubCommand(new TargetSCmd(instance));
        registerSubCommand(new OnlineSCmd(instance));
        registerSubCommand(new UsernameSCmd(instance));
    }
}
