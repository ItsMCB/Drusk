package me.itsmcb.drusk.features.drusk;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import org.jetbrains.annotations.NotNull;

public class DruskCMD extends CustomCommand {

    private Drusk instance;

    public DruskCMD(@NotNull Drusk instance) {
        super("drusk","","drusk.admin");
        this.instance = instance;
        registerSubCommand(new ReloadSCmd(instance));
    }
}
