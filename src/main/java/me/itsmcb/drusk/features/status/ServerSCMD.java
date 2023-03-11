package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import me.itsmcb.vexelcore.common.api.text.Icon;
import me.itsmcb.vexelcore.common.api.vendor.MinecraftSoftwareVendor;
import me.itsmcb.vexelcore.common.api.vendor.MinecraftSoftwareVendorUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.stream.Collectors;

public class ServerSCMD extends CustomCommand {

    private Drusk instance;

    public ServerSCMD(Drusk instance) {
        super("server","View status of the server","drusk.admin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        File folderFile = new File(instance.getDataFolder().getParent()).getAbsoluteFile();
        String folderName = folderFile.getParentFile().getName();

        new BukkitMsgBuilder("&7========== &3&l"+folderName+" &r&7==========")
                .hover("&e"+ Icon.TRIANGLE_POINTING_RIGHT+" &7Click to copy path:&b\n"+folderFile.getParentFile().getAbsolutePath())
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, folderFile.getParentFile().getAbsolutePath()).send(player);

        // Send vendor information
        if (MinecraftSoftwareVendorUtil.vendorExists(instance.getServer().getName())) {
            MinecraftSoftwareVendor vendor = MinecraftSoftwareVendorUtil.getVendorInformation(MinecraftSoftwareVendorUtil.getVendor(instance.getServer().getName()));
            Msg.send(player, Msg.sendOneLine(
                    Msg.componentize("&3Type: &b"+vendor.getVendor().getVendor()),
                    formatValueInfo(vendor.getWebsite(), "Website"),
                    formatValueInfo(vendor.getDocumentation(), "Documentation"),
                    formatValueInfo(vendor.getBuilds(), "Builds"),
                    formatValueInfo(vendor.getDiscord(), "Discord"),
                    formatValueInfo(vendor.getSource(), "Source"),
                    formatValueInfo(vendor.getIssues(), "Issues")
            ));
        } else {
            Msg.send(player, "&3Type: &b"+instance.getServer().getName());
        }
        // Version
        new BukkitMsgBuilder("&3Version: &b"+instance.getServer().getVersion()+" &7(" + instance.getServer().getBukkitVersion()+")")
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,instance.getServer().getVersion())
                .send(player);
        // Whitelist
        new BukkitMsgBuilder("&3Whitelist: &b"+instance.getServer().isWhitelistEnforced())
                .hover("&3Whitelisted Players: \n&b"+instance.getServer().getWhitelistedPlayers().stream().map(OfflinePlayer::getName).collect(Collectors.joining(", ")))
                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/whitelist ")
                .send(player);
        // Default GM
        new BukkitMsgBuilder("&3Default Game Mode: &b"+instance.getServer().getDefaultGameMode().name())
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,instance.getServer().getDefaultGameMode().name())
                .send(player);
        // Port
        new BukkitMsgBuilder("&3Port: &b"+instance.getServer().getPort())
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,instance.getServer().getPort()+"")
                .send(player);
        // Players
        new BukkitMsgBuilder("&3Players: &b"+instance.getServer().getOnlinePlayers().size()+"&7/&b" + instance.getServer().getMaxPlayers())
                .send(player);
        // Nether Enabled
        new BukkitMsgBuilder("&3Nether Enabled: &b"+instance.getServer().getAllowNether())
                .send(player);
        // End Enabled
        new BukkitMsgBuilder("&3End Enabled: &b"+instance.getServer().getAllowEnd())
                .send(player);
    }

    private Component formatValueInfo(String value, String valueName) {
        Component spacer = Msg.componentize("&7 | ");
        if (value != null) {
            return spacer.append(Msg.openWebsite("&b"+valueName, "&3Click to open &b"+value, value));
        }
        return null;
    }
}
