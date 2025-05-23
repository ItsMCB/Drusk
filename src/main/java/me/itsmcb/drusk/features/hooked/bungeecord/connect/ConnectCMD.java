package me.itsmcb.drusk.features.hooked.bungeecord.connect;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.VexelCoreBukkitAPI;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConnectCMD extends CustomCommand {

    private Drusk instance;

    public ConnectCMD(Drusk instance) {
        super("connect","Create a proxy transfer request through this backend server", "");
        this.instance = instance;
        addParameter("[serverName]","The ID of the backend server to connect to");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        if (args.length > 0) {
            VexelCoreBukkitAPI.refreshProxyServerNameCache();
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> {
                // Check to see if desired server is present
                if (VexelCoreBukkitAPI.getProxyServerNamesCache().stream().anyMatch(server -> server.equals(args[0]))) {
                    // Present, send player
                    Msg.send(player, "&3Connecting to &b" + args[0]);
                    ByteArrayDataOutput outCon = ByteStreams.newDataOutput();
                    outCon.writeUTF("Connect");
                    outCon.writeUTF(args[0]);
                    player.sendPluginMessage(instance, "BungeeCord", outCon.toByteArray());
                } else {
                    player.sendMessage("That server doesn't exist. Maybe check the spelling?");
                }
            }, 6L);
            return;
        }
        help(player);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return VexelCoreBukkitAPI.getProxyServerNamesCache();
    }
}
