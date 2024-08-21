package me.itsmcb.drusk.features.skin.copy;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.ParticleUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import me.itsmcb.vexelcore.bukkit.plugin.CachedPlayer;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
        CachedPlayer cp = instance.getCacheManager().get(args[0]);
        if (!cp.isComplete()) {
            new BukkitMsgBuilder("&cAn error occurred. Does that player exist?").send(player);
            return;
        }
        PlayerUtils.setSkin(player,cp.getPlayerSkin().getValue(),cp.getPlayerSkin().getSignature());
        new BukkitRunnable() {
            @Override
            public void run() {
                //OnlinePlayerSkin skinInformation = PlayerUtils.setRealSkin(player, args[0], instance);
                ParticleUtils.spawnCircle(
                        0.5,
                        12.0,
                        player.getLocation(),
                        Particle.REDSTONE,
                        new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F)
                );
            }
        }.runTaskAsynchronously(instance);

    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }

}
