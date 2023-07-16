package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.drusk.features.skin.copy.CopySCmd;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
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
        registerSubCommand(new SaveSCmd(instance));
        addParameter("reset", "Resets skin to default.");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (args.length > 0 && args[0].equalsIgnoreCase("reset")) {
                    PlayerUtils.setRealSkin(player, player, instance);
                    new BukkitMsgBuilder("&aReset your skin").send(player);
                    return;
                }
                player.sendMessage(help());
            }
        }.runTaskAsynchronously(instance);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return List.of("reset");
    }
}
