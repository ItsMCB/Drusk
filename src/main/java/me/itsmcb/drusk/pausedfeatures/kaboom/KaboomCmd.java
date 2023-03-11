package me.itsmcb.drusk.pausedfeatures.kaboom;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class KaboomCmd extends CustomCommand {
    public KaboomCmd() {
        super("kaboom", "Experimental TNT!", "drusk.kaboom");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        Location playerLocation = player.getLocation();
        TNTPrimed tnt = playerLocation.getWorld().spawn(playerLocation, TNTPrimed.class);
        switch (args[0]) {
            case "1" -> {
                setTNTData(tnt, false, 20*3, 10, false, "&c&lGreat TNT");
            }
            case "2" -> {
                setTNTData(tnt, true, 20*2, 20, true, "&c&lBruh TNT");
            }
            case "3" -> {
                setTNTData(tnt, true, 20*2, 30, true, "&c&lOOf TNT");
                tnt.getLocation().getWorld().spawn(tnt.getLocation(), TNTPrimed.class);
            }
            case "4" -> {
                setTNTData(tnt, true, 20*2, 40, true, "&c&lAYO TNT");
            }
            case "5" -> {
                setTNTData(tnt, true, 20*2, 50, true, "&c&lNice TNT");
            }
            case "6" -> {
                setTNTData(tnt, true, 1, 60, true, "&c&lSwag TNT");

                Location location2 = playerLocation;
                location2.setY(tnt.getLocation().getY()+10);
                TNTPrimed tnt2 = playerLocation.getWorld().spawn(location2, TNTPrimed.class);
                setTNTData(tnt2, true, 1, 60, true, "&c&lSwag TNT");

                Location location3 = playerLocation;
                location3.setY(tnt.getLocation().getY()-15);
                TNTPrimed tnt3 = playerLocation.getWorld().spawn(location3, TNTPrimed.class);
                setTNTData(tnt3, true, 1, 60, true, "&c&lSwag TNT");
            }
        }
    }

    private void setTNTData(TNTPrimed tnt, boolean incendiary, int fuseTicks, int yield, boolean glowing, String customName) {
        tnt.setIsIncendiary(incendiary);
        tnt.setFuseTicks(fuseTicks);
        tnt.setYield(yield);
        tnt.setGlowing(glowing);
        tnt.setCustomNameVisible(true);
        tnt.customName(new BukkitMsgBuilder(customName).get());
    }
}
