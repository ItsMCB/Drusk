package me.itsmcb.drusk.features.drusk;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.experience.AudioResponse;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DruskCMD extends Command {

    private Drusk instance;

    public DruskCMD(@NotNull Drusk instance) {
        super("drusk");
        this.instance = instance;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (instance.getPermissionManager().lacks(sender, "admin")) {
            new BukkitMsgBuilder(instance.getLocalizationManager().get("error-permission")).send(sender);
            return true;
        }
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.isCalling("reload")) {
            Msg.send(sender, "&7Reloading...");
            instance.getMainConfig().reload();
            instance.getBukkitFeatureManager().reload();
            Msg.sendResponsive(AudioResponse.SUCCESS, sender, "&aReload complete");
            return true;
        }
        if (cmdHelper.isCalling("regenchunk")) {
            Msg.send(sender, "&7Regenerating chunk!");
            if (sender instanceof Player player) {
                // It seems to work!
                // TODO implement this in Voyage
                player.getWorld().regenerateChunk(player.getChunk().getX(),player.getChunk().getZ());
            }
            return true;
        }
        new BukkitMsgBuilder("Options: /drusk reload - Reloads config").send(sender);
        // TODO send proper usage
        return false;
    }
}
