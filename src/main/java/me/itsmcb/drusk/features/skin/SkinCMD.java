package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import me.itsmcb.vexelcore.common.api.web.mojang.PlayerSkinInformation;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SkinCMD extends Command {

    private Drusk instance;

    public SkinCMD(@NotNull Drusk instance) {
        super("skin");
        this.instance = instance;
    }

    public void spawnCircle(double radius, double stepSize, Location loc) {
        double x = 0;
        double z = 0;
        for(double i=0; i<360; i+= stepSize) {
            x = (Math.sin(i) * radius) + loc.getX();
            z = (Math.cos(i) * radius) + loc.getZ();
            Location spawnLocation = new Location(loc.getWorld(), x, loc.getY(), z);
            //Entity as = loc.getWorld().spawnEntity(new Location(loc.getWorld(), x, loc.getY(), z), EntityType.ARMOR_STAND);
            /*
            TNTPrimed tnt = (TNTPrimed)loc.getWorld().spawnEntity(new Location(loc.getWorld(), x, loc.getY(), z), EntityType.PRIMED_TNT);
            tnt.setFuseTicks(20 * 4);
            tnt.setVelocity(tnt.getLocation().subtract(loc).toVector().normalize().multiply(1));

             */
            //as.setVelocity(as.getLocation().subtract(loc).toVector().normalize().multiply(1));

            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F);
            loc.getWorld().spawnParticle(Particle.REDSTONE, spawnLocation, 1, dustOptions);
        }
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (instance.getPermissionManager().lacks(sender, "admin")) {
            new BukkitMsgBuilder(instance.getLocalizationManager().get("error-permission")).send(sender);
            return true;
        }
        if (sender instanceof Player player) {
            CMDHelper cmdHelper = new CMDHelper(args);
            if (cmdHelper.isCalling("set")) {
                if (cmdHelper.argExists(1)) {
                    PlayerSkinInformation skinInformation = PlayerUtils.setAnotherSkin(player, args[1], instance);
                    if (!(skinInformation.isInformationComplete())) {
                        Msg.send(sender, "&cError occurred. Does that player exist?");
                        return true;
                    }
                    Msg.send(player, "&bUpdated skin to display as &3"+skinInformation.getPlayerName());
                    spawnCircle(0.5, 12.0, player.getLocation());
                }
                return true;
            }
            if (cmdHelper.isCalling("copy")) {
                Entity targetEntity = player.getTargetEntity(6);

                if (targetEntity instanceof Player targetPlayer) {
                    targetPlayer.getPlayerProfile().getProperties().forEach(profileProperty -> {
                        if (profileProperty.getName().equals("textures")) {
                            PlayerUtils.setAnotherSkin(player, profileProperty.getValue(), profileProperty.getSignature());
                            Msg.send(sender, "&bUpdated skin to display as &b"+targetPlayer.getName());
                        }
                    });
                    return true;
                }
                Msg.send(sender, "&cInvalid target");
            }
            return true;
        }
        Msg.send(sender, "&cOnly players can execute this command.");
        return false;
    }
}
