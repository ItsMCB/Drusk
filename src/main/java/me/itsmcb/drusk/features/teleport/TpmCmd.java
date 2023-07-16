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
        super("tpm", "Teleport to middle of block", "drusk.tp.m");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (!(cmdHelper.isInt(0) && cmdHelper.isInt(1))) {
            new BukkitMsgBuilder("&cFirst and second argument must be integers!").send(player);
            return;
        }
        double x = Integer.parseInt(args[0])+.5;
        double z = Integer.parseInt(args[1])+.5;
        // /tpm x z
        if (args.length == 2) {
            teleportPlayer(player, player.getWorld(),x,player.getWorld().getHighestBlockYAt((int) x, (int) z)+1,z);
            return;
        }
        // tpm x y z
        if (!(cmdHelper.isInt(2))) {
            new BukkitMsgBuilder("&cThird argument must be an integer!").send(player);
            return;
        }
        double y = Integer.parseInt(args[1])+.5;
        z = Integer.parseInt(args[2])+.5;
        if (!(cmdHelper.argExists(3))) {
            teleportPlayer(player, player.getWorld(),x,y,z);
            return;
        }
        // tpm x y z [username]
        Player selectedPlayer = Bukkit.getPlayer(args[3]);
        if (selectedPlayer == null) {
            new BukkitMsgBuilder("&cThat player isn't online!").send(player);
            return;
        }
        teleportPlayer(selectedPlayer,player.getWorld(),x,y,z);
    }

    private void teleportPlayer(Player player, World world, double x, double y, double z) {
        Location location = new Location(world,x,y,z);
        player.teleport(location);
        new BukkitMsgBuilder("&7Teleported to &d" +world.getName()+"&7 at &d"+x+", "+y+", "+z).send(player);
    }
}
