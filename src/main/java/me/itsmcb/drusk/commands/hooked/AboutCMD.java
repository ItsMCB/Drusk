package me.itsmcb.drusk.commands.hooked;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class AboutCMD implements CommandExecutor {

    private Drusk instance;

    public AboutCMD(Drusk instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // TODO check perms
        // server, status, player
        Msg.send(sender,
                "&3Implementation Name &b: " + instance.getServer().getName(),
                "&3Implementation Version &b: " + instance.getServer().getVersion() + " &7(" + instance.getServer().getBukkitVersion() + ")",
                "&3Players &b: " + instance.getServer().getOnlinePlayers().size()+" &7/ &b" + instance.getServer().getMaxPlayers(),
                "&3Port &b: " + instance.getServer().getPort(),
                "&3Server folder name &b: " + new File(instance.getDataFolder().getParent()).getAbsoluteFile().getParentFile().getName(),
                "&3End Enabled &b: " + instance.getServer().getAllowEnd(),
                "&3Nether Enabled &b: " + instance.getServer().getAllowNether()
                );
        return false;
    }
}
