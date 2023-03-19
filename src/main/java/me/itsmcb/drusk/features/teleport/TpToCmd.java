package me.itsmcb.drusk.features.teleport;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TpToCmd extends CustomCommand {
    public TpToCmd() {
        super("tpto", "Teleport player to another", "drusk.tpto");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (!cmdHelper.argExists(0)) {
            player.sendMessage(help());
            return;
        }
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            new BukkitMsgBuilder("&cThat player is offline!").send(player);
            return;
        }
        player.teleport(targetPlayer.getLocation());
        new BukkitMsgBuilder("&aTeleported to &e" + targetPlayer.getName()).send(player);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
