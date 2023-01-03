package me.itsmcb.drusk.features.spawn;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.WorldUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCMD extends Command {

    private Drusk instance;

    protected SpawnCMD(Drusk instance) {
        super("spawn");
        this.instance = instance;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (sender instanceof Player player) {
            if (cmdHelper.isCalling("set")) {
                Location location = player.getLocation();
                if (cmdHelper.flagExists("--center")) {
                    location.setX(location.getBlockX()+0.5);
                    location.setZ(location.getBlockZ()+0.5);
                }
                player.getWorld().setSpawnLocation(location);
                instance.getMainConfig().get().set("spawn.location", location);
                instance.getMainConfig().save();
                new BukkitMsgBuilder("&eSet world spawn!").send(sender);
                return true;
            }
            if (!instance.getMainConfig().get().is("spawn.location", Location.class)) {
                new BukkitMsgBuilder("&eSpawn location not yet set!").send(sender);
                return true;
            }
            Location location = (Location) instance.getMainConfig().get().get("spawn.location", Location.class);
            if (!WorldUtils.isLoaded(location.getWorld().getName())) {
                new BukkitMsgBuilder("&eWorld not found! Is it loaded?").send(sender);
                return true;
            }
            player.teleport(location);
            new BukkitMsgBuilder("&eTeleported to spawn location").send(sender);
        }
        return true;
    }
}
