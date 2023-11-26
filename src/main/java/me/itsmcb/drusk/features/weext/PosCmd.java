package me.itsmcb.drusk.features.weext;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import org.bukkit.entity.Player;

public class PosCmd extends CustomCommand {
    int pos;

    public PosCmd(int pos) {
        super(pos+"", "", "");
        this.pos = pos;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        player.performCommand("/pos"+pos);
    }
}
