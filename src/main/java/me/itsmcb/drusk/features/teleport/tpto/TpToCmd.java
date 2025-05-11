package me.itsmcb.drusk.features.teleport.tpto;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.drusk.features.teleport.TeleportRequest;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TpToCmd extends CustomCommand {

    Drusk instance;

    public TpToCmd(Drusk instance) {
        super("tpto", "Teleport player to another", "drusk.tpto");
        this.instance = instance;
        registerSubCommand(new TpToAcceptCmd(instance));
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
        if (player.hasPermission("drusk.admin")) {
            player.teleport(targetPlayer.getLocation());
            new BukkitMsgBuilder("&aAdmin teleported to &e" + targetPlayer.getName()).send(player);
            return;
        }
        new BukkitMsgBuilder("&7Teleport request sent to &d"+targetPlayer.getName()).send(player);
        new BukkitMsgBuilder("&d&l(!) &d"+player.getName()+"&7 requested to teleport to you. You have 5 minutes to accept. Click or type &d/tpto accept "+player.getName())
                .clickEvent(ClickEvent.Action.RUN_COMMAND,"/tpto accept "+player.getName())
                .hover("&7Click to accept")
                .send(targetPlayer);
        String reason = cmdHelper.argExists(1) ? args[1] : null;
        instance.getTeleportRequestManager().add(new TeleportRequest(player,targetPlayer,reason));
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
