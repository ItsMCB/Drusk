package me.itsmcb.drusk.features.skin.copy;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.VexelCoreBukkitAPI;
import me.itsmcb.vexelcore.bukkit.api.cache.CacheManagerV2;
import me.itsmcb.vexelcore.bukkit.api.cache.CachedPlayerV2;
import me.itsmcb.vexelcore.bukkit.api.cache.exceptions.DataRequestFailure;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menu.MenuButton;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.ParticleUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import me.itsmcb.vexelcore.bukkit.plugin.CachedPlayer;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class UsernameSCmd extends CustomCommand {

    private Drusk instance;

    public UsernameSCmd(Drusk instance) {
        super("username", "Copy a skin by username", "drusk.skin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.argNotExists(0)) {
            new BukkitMsgBuilder("&cProvide a username").send(player);
            return;
        }
        String username = cmdHelper.getStringOfArgsAfterIndex(-1);
        if (!CacheManagerV2.isValidUsernameFormat(username)) {
            new BukkitMsgBuilder("&cInvalid username format").send(player);
            return;
        }
        // TODO update before application
        VexelCoreBukkitAPI.getCacheManager().getCachedPlayer(username)
                .exceptionally(e -> {
                    new BukkitMsgBuilder("&cAn error occurred. Does that player exist?").send(player);
                    return null;
                })
                .thenAccept(p -> {
                    PlayerUtils.setSkin(player,p.getPlayerSkinData().getTexture(),p.getPlayerSkinData().getSignature());
                });
        new BukkitRunnable() {
            @Override
            public void run() {
                ParticleUtils.spawnCircle(
                        0.5,
                        12.0,
                        player.getLocation(),
                        Particle.POOF,
                        new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F)
                );
            }
        }.runTaskAsynchronously(instance);

    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return VexelCoreBukkitAPI.getCacheManager().getAllPlayerUsernames();
    }

}
