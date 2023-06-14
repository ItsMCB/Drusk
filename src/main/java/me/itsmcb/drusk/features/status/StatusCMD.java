package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;

public class StatusCMD extends CustomCommand {

    private Drusk instance;

    public StatusCMD(Drusk instance) {
        super("status","View the status of various server aspects","drusk.admin");
        this.instance = instance;
        registerSubCommand(new PlayerSCMD(instance));
        registerSubCommand(new PlayerDataSCMD(instance));
        registerSubCommand(new ServerSCMD(instance));
        registerSubCommand(new JVMSCMD(instance));
        registerSubCommand(new PlayersSCmd(instance));
        registerSubCommand(new StorageSCmd(instance));
        registerSubCommand(new PlaytimeSCmd(instance));
    }
}
