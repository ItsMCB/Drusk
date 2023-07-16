package me.itsmcb.drusk.features.teleport.tpto;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TpToAcceptCmd extends CustomCommand {

    Drusk instance;

    public TpToAcceptCmd(Drusk instance) {
        super("accept", "Accept another players teleport request", "drusk.tpto");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        BukkitMsgBuilder error = new BukkitMsgBuilder("&cPlease provide a valid online player username to accept once they've sent you a request.");
        if (cmdHelper.argNotExists(0)) {
            error.send(player);
            return;
        }
        Player accepted = Bukkit.getPlayer(args[0]);
        if (accepted == null) {
            error.send(player);
            return;
        }
        instance.getTeleportRequestManager().accept(player,accepted);
        return;
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        // TODO just return requested
        return BukkitUtils.getOnlinePlayerNames();
    }
}
