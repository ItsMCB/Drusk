package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerSCMD extends CustomCommand {

    private Drusk instance;

    public PlayerSCMD(Drusk instance) {
        super("player","View status of a player","drusk.admin");
        this.instance = instance;
    }

    @Override
    public List<String> getCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        // Custom command to do: require arg
        if (cmdHelper.argExists(0)) {
            Player selectedPlayer = Bukkit.getPlayer(args[0]);
            if (selectedPlayer == null) {
                new BukkitMsgBuilder("&Not online").send(player);
                return;
            }
            new BukkitMsgBuilder(
                    "" + "\n" +
                            "&eUsername: " + selectedPlayer.getName() + " \n" +
                            "&ePing: " + selectedPlayer.getPing() + " \n" +
                            "&eFlyspeed: " + selectedPlayer.getFlySpeed() + " \n" +
                            "&eLP Primary Group: " + instance.getServer().getServicesManager().load(LuckPerms.class).getUserManager().getUser(selectedPlayer.getUniqueId()).getPrimaryGroup() + " \n" +
                            "&eIP: " + selectedPlayer.getAddress().getAddress() + " \n" +
                            "&eClient Brand: " + selectedPlayer.getClientBrandName() + " \n"
            ).send(player);
        }


    }
}
