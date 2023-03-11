package me.itsmcb.drusk.features.weext;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import org.bukkit.entity.Player;

public class WCmd extends CustomCommand {

    public WCmd() {
        super("w", "", "worldedit.wand");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        player.performCommand("/wand");
    }
}
