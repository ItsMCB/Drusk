package me.itsmcb.drusk.features.teleport;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TopCmd extends CustomCommand {
    public TopCmd() {
        super("top", "Teleport to the top", "drusk.top");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        if (args.length == 0) {
            player.teleport(teleportToHighestSolidLocation(player.getLocation()));
            new BukkitMsgBuilder("&7Teleported &3yourself &7to the highest relative location.").send(player);
            return;
        }
        if (player.hasPermission("drusk.top.others")) {
            Player selected = Bukkit.getPlayer(args[0]);
            if (selected == null || !selected.isOnline()) {
                new BukkitMsgBuilder("&cPlayer isn't online!").send(player);
                return;
            }
            selected.teleport(teleportToHighestSolidLocation(selected.getLocation()));
            new BukkitMsgBuilder("&7Teleported &3"+selected.getName()+" &7to their highest relative location").send(player);
        }

    }

    private static Location teleportToHighestSolidLocation(Location loc) {
        Location original = loc;
        loc = loc.toHighestLocation();
        if (isSafeBlock(loc)) {
            return LocationUtils.toCenter(loc.add(0,1,0));
        }
        for (int i = loc.getBlockY(); i > loc.getWorld().getMinHeight(); i--) {
            loc.set(loc.getX(),i,loc.getZ());
            if (isSafeBlock(loc)) {
                return LocationUtils.toCenter(loc.add(0,1,0));
            }
        }
        return original;
    }

    public static boolean isSafeBlock(Location loc) {
        String value = loc.getBlock().getType().key().value();
        if (value.contains("fence") || value.contains("trapdoor") | value.contains("button") || value.contains("trapdoor")
                || value.contains("sign") || value.contains("wall") || value.contains("bud") || value.contains("vine")
        || value.contains("ladder") || value.contains("glow") || value.contains("web") || value.contains("torch")
        || value.contains("tripwire") || value.contains("coral_fan") || value.contains("coral_wall_fan") || value.contains("fire")) {
            return false;
        }
        return loc.getBlock().getType().isSolid();
    }


    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
