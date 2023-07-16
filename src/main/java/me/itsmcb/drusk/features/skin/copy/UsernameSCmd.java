package me.itsmcb.drusk.features.skin.copy;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import me.itsmcb.vexelcore.bukkit.api.utils.ParticleUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import me.itsmcb.vexelcore.common.api.web.mojang.OnlinePlayerSkin;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class UsernameSCmd extends CustomCommand {

    private Drusk instance;

    public UsernameSCmd(Drusk instance) {
        super("username", "Copy a skin from Mojang by a player's username", "drusk.skin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        if (args.length == 0) {
            new BukkitMsgBuilder("&cProvide a username").send(player);
            return;
        }
        OnlinePlayerSkin skinInformation = PlayerUtils.setRealSkin(player, args[0], instance);
        if (!(skinInformation.isInformationComplete())) {
            Msg.send(player, "&cError occurred. Does that player exist?");
            return;
        }
        ParticleUtils.spawnCircle(
                0.5,
                12.0,
                player.getLocation(),
                Particle.REDSTONE,
                new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F)
        );

    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }

}
