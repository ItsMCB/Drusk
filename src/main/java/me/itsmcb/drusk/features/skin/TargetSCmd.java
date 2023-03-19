package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TargetSCmd extends CustomCommand {
    public TargetSCmd(Drusk instance) {
        super("target", "Copy skin from the player or NPC you're looking at.", "drusk.skin");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        Entity targetEntity = player.getTargetEntity(7);
        if (targetEntity == null) {
            new BukkitMsgBuilder("&cNo target entity found").send(player);
            return;
        }

        if (targetEntity instanceof Player target) {
            PlayerUtils.copy(target,player);
            return;
        }
        new BukkitMsgBuilder("&cInvalid target.").send(player);
    }
}
