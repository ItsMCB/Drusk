package me.itsmcb.drusk.features.gamemode;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CreativeCmd extends CustomCommand {

    private final Drusk instance;

    public CreativeCmd(Drusk instance, String commandName) {
        super(commandName, "Switch game mode to creative.", "drusk.gamemode.self.creative");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.argExists(0) && player.hasPermission("drusk.gamemode.other.creative")) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                new BukkitMsgBuilder("&cPlayer isn't online!").send(player);
                return;
            }
            GamemodeFeat.setGameMode(player, GameMode.CREATIVE, target);
            return;
        }
        GamemodeFeat.setGameMode(player, GameMode.CREATIVE, player);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
