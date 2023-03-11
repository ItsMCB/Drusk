package me.itsmcb.drusk.features.inventory;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class OpenSCmd extends CustomCommand {

    public OpenSCmd() {
        super("open", "View and manipulate inventory of another player", "drusk.admin");
        super.addParameter("[username]","The player whose inventory will be opened");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.argExists(0)) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                new BukkitMsgBuilder("&cThat player is offline!").send(player);
                return;
            }
            if (target == player) {
                new BukkitMsgBuilder("&cOpen your inventory normally, nerd.").send(player);
                return;
            }
            player.openInventory(target.getInventory());
            new BukkitMsgBuilder("&7Inventory of &a" + target.getName() + "&7 opened.").send(player);
        }
    }

    @Override
    public List<String> getAdditionalCompletions() {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
