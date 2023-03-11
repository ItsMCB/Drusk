package me.itsmcb.drusk.features.gamemode;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;

public class AdventureCmd extends CustomCommand {

    private final Drusk instance;

    public AdventureCmd(Drusk instance, String commandName) {
        super(commandName, "Adventure Mode", "drusk.gamemode.adventure");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.argExists(0) && player.hasPermission("drusk.gamemode.adventure.other")) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                new BukkitMsgBuilder("&cPlayer isn't online!").send(player);
                return;
            }
            GamemodeFeat.setGameMode(player, GameMode.ADVENTURE, target);
            return;
        }
        GamemodeFeat.setGameMode(player, GameMode.ADVENTURE, player);
    }

    @Override
    public List<String> getAdditionalCompletions() {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
