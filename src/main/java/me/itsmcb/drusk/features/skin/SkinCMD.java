package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.drusk.features.skin.copy.CopySCmd;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import me.itsmcb.vexelcore.common.api.utils.ArgUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkinCMD extends CustomCommand {

    private Drusk instance;

    public SkinCMD(@NotNull Drusk instance) {
        super("skin","Set custom skin","drusk.skin");
        this.instance = instance;
        registerSubCommand(new CopySCmd(instance));
        registerSubCommand(new SelectSCmd(instance));
        registerSubCommand(new CachedSCmd(instance));
        registerSubCommand(new SaveSCmd(instance));
        addParameter("reset", "Resets skin to default.");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.isCalling("reset")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    PlayerUtils.setRealSkin(player, player, instance);
                    new BukkitMsgBuilder("&aReset your skin").send(player);
                }
            }.runTaskAsynchronously(instance);
            return;
        }
        new SelectSCmd(instance).execute(player, ArgUtils.shift(args));
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return List.of("reset");
    }
}
