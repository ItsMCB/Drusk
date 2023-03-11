package me.itsmcb.drusk.features.inventory;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ClearSCmd extends CustomCommand {

    public ClearSCmd() {
        super("clear", "Clears inventory of player", "drusk.admin");
        super.addParameter("[username]","The player whose inventory will be cleared");
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
            target.getInventory().clear();
            new BukkitMsgBuilder("&7Inventory of &a" + target.getName() + "&7 cleared.").send(player);
        }
    }

    @Override
    public List<String> getAdditionalCompletions() {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
