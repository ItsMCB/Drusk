package me.itsmcb.drusk.features.spawn;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpawnSetSCmd extends CustomCommand {

    private Drusk instance;

    public SpawnSetSCmd(@NotNull Drusk instance) {
        super("set","Set the spawn location","drusk.spawn.set");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        Location location = player.getLocation();
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.flagExists("--center")) {
            location.setX(location.getBlockX()+0.5);
            location.setZ(location.getBlockZ()+0.5);
        }
        // Check if firstjoin or regular
        if (cmdHelper.argEquals(0,"firstjoin")) {
            instance.getMainConfig().get().set("spawn.first-join-location", location);
            instance.getMainConfig().save();
            new BukkitMsgBuilder("&eSet first join spawn").send(player);
        } else {
            // Regular
            player.getWorld().setSpawnLocation(location);
            instance.getMainConfig().get().set("spawn.location", location);
            instance.getMainConfig().save();
            new BukkitMsgBuilder("&eSet world spawn!").send(player);
        }
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return List.of("firstjoin","regular");
    }
}
