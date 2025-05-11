package me.itsmcb.drusk.features.teleport;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TpHereCmd extends CustomCommand {
    public TpHereCmd() {
        super("tphere", "Teleport one player to another", "drusk.tphere");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (!cmdHelper.argExists(0)) {
            help(player);
            return;
        }
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            new BukkitMsgBuilder("&cThat player is offline!").send(player);
            return;
        }
        targetPlayer.teleport(player.getLocation());
        new BukkitMsgBuilder("&aTeleported &e" + targetPlayer.getName() +"&a to you.").send(player);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
