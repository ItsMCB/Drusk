package me.itsmcb.drusk.features.hooked.bungeecord.connect;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.VexelCoreBukkitAPI;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConnectCMD extends Command {

    private Drusk instance;

    public ConnectCMD(Drusk instance) {
        super("connect");
        this.instance = instance;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (sender instanceof Player player && args.length > 0) {
            VexelCoreBukkitAPI.refreshProxyServerNameCache();
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> {
                // Check to see if desired server is present
                if (VexelCoreBukkitAPI.getProxyServerNamesCache().stream().anyMatch(server -> server.equals(args[0]))) {
                    // Present, send player
                    Msg.send(sender, "&3Connecting to &b" + args[0]);
                    ByteArrayDataOutput outCon = ByteStreams.newDataOutput();
                    outCon.writeUTF("Connect");
                    outCon.writeUTF(args[0]);
                    player.sendPluginMessage(instance, "BungeeCord", outCon.toByteArray());
                } else {
                    player.sendMessage("That server doesn't exist. Maybe check the spelling?");
                }
            }, 6L);
        }
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return VexelCoreBukkitAPI.getProxyServerNamesCache();
    }
}
