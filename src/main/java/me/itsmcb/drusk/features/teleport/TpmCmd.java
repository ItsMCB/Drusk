package me.itsmcb.drusk.features.teleport;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TpmCmd extends CustomCommand {

    private Drusk instance;

    public TpmCmd(Drusk instance) {
        super("tpm", "Teleport to middle of current block", "drusk.tp.m");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        double x = player.getLocation().getBlockX()+0.5;
        double z = player.getLocation().getBlockZ()+0.5;
        // TODO w/ YAW option as NORTH, NORTHEAST, etc.
        teleportPlayer(player,player.getWorld(),x,player.getLocation().getY(),z);
    }

    private void teleportPlayer(Player player, World world, double x, double y, double z) {
        Location location = new Location(world,x,y,z);
        player.teleport(location);
        new BukkitMsgBuilder("&7Teleported to &d" +world.getName()+"&7 at &d"+x+", "+y+", "+z).send(player);
    }
}
