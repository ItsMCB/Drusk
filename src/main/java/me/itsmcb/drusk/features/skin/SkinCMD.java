package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import me.itsmcb.vexelcore.bukkit.api.utils.ParticleUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import me.itsmcb.vexelcore.common.api.web.mojang.PlayerSkinInformation;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkinCMD extends CustomCommand {

    private Drusk instance;

    public SkinCMD(@NotNull Drusk instance) {
        super("skin","Set custom skin","drusk.skin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.isCalling("set")) {
            if (cmdHelper.argExists(1)) {
                PlayerSkinInformation skinInformation = PlayerUtils.setAnotherSkin(player, args[1], instance);
                if (!(skinInformation.isInformationComplete())) {
                    Msg.send(player, "&cError occurred. Does that player exist?");
                    return;
                }
                Msg.send(player, "&bUpdated skin to display as &3"+skinInformation.getPlayerName());
                ParticleUtils.spawnCircle(
                        0.5,
                        12.0,
                        player.getLocation(),
                        Particle.REDSTONE,
                        new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F)
                );
            }
            return;
        }
        if (cmdHelper.isCalling("copy")) {
            Entity targetEntity = player.getTargetEntity(6);

            if (targetEntity instanceof Player targetPlayer) {
                targetPlayer.getPlayerProfile().getProperties().forEach(profileProperty -> {
                    if (profileProperty.getName().equals("textures")) {
                        PlayerUtils.setAnotherSkin(player, profileProperty.getValue(), profileProperty.getSignature());
                        Msg.send(player, "&bUpdated skin to display as &b"+targetPlayer.getName());
                    }
                });
                return;
            }
            Msg.send(player, "&cInvalid target");
        }
    }

    @Override
    public List<String> getAdditionalCompletions() {
        return List.of("set","copy");
    }
}
