package me.itsmcb.drusk.features.gamemode;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;

public class GamemodeCmd extends CustomCommand {

    public GamemodeCmd(Drusk instance) {
        super("gamemode", "", "drusk.gamemode");
        registerSubCommand(new CreativeCmd(instance,"creative"));
        registerSubCommand(new SurvivalCmd(instance, "survival"));
        registerSubCommand(new AdventureCmd(instance, "adventure"));
        registerSubCommand(new SpectatorCmd(instance, "spectator"));
    }

}
