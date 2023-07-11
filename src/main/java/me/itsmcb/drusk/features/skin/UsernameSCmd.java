package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menuv2.InputMenu;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import me.itsmcb.vexelcore.bukkit.api.utils.ParticleUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import me.itsmcb.vexelcore.common.api.web.mojang.OnlinePlayerSkin;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

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
            // Ask for username with menu
            InputMenu inputMenu = new InputMenu("What username?",new MenuV2Item(Material.PLAYER_HEAD).name("..."),player)
                    .string(e -> {
                        new BukkitMsgBuilder("You said this: "+e).send(player);
                    });
            instance.getMenuManager().open(inputMenu, player);
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
}
