package me.itsmcb.drusk.features.talk;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.common.api.utils.ArgUtils;
import org.bukkit.entity.Player;

public class SayCmd extends CustomCommand {

    private Drusk instance;

    public SayCmd(Drusk instance) {
        super("say", "Command for saying something", "");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        player.chat(ArgUtils.mergeWithSpace(args,0));
    }
}
