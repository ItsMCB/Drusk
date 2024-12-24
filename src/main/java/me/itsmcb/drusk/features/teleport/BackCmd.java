package me.itsmcb.drusk.features.teleport;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.voyage.api.VoyageWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;

public class BackCmd extends CustomCommand {
    public BackCmd() {
        super("back", "Go back to a previous location", "drusk.back");
    }

    private Drusk instance;

    public BackCmd(Drusk instance) {
        this();
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        if (!instance.getLastTeleportLocation().containsKey(player.getUniqueId())) {
            new BukkitMsgBuilder("&7You haven't teleported recently so there's nowhere to teleport you back to.").send(player);
            return;
        }
        Location loc = instance.getLastTeleportLocation().get(player.getUniqueId());
        if (!Bukkit.getWorlds().contains(loc.getWorld())) {
            File possibleWorld = new File(Bukkit.getWorldContainer()+File.separator+loc.getWorld().getName());
            if (!possibleWorld.exists()) {
                new BukkitMsgBuilder("&cUh oh! The world you were about to teleport to can't be found. Was it deleted?").send(player);
                return;
            }
            VoyageWorld vw = new VoyageWorld(loc.getWorld());
            vw.load();
            loc.setWorld(vw.getWorld());
        }
        player.teleport(loc);
    }
}
