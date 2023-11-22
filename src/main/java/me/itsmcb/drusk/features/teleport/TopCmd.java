package me.itsmcb.drusk.features.teleport;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import org.bukkit.Bukkit;
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
            player.teleport(player.getLocation().toHighestLocation());
            new BukkitMsgBuilder("&7Teleported &3yourself &7to the highest relative location.").send(player);
            return;
        }
        if (player.hasPermission("drusk.top.others")) {
            Player selected = Bukkit.getPlayer(args[0]);
            if (selected == null || !selected.isOnline()) {
                new BukkitMsgBuilder("&cPlayer isn't online!").send(player);
                return;
            }
            player.teleport(player.getLocation().toHighestLocation());
            new BukkitMsgBuilder("&7Teleported &3"+selected.getName()+"&7to the highest relative location").send(player);
        }

    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
