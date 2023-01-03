package me.itsmcb.drusk.features.info;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import me.itsmcb.vexelcore.bukkit.api.utils.WorldUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import me.itsmcb.vexelcore.common.api.text.Icon;
import me.itsmcb.vexelcore.common.api.vendor.MinecraftSoftwareVendor;
import me.itsmcb.vexelcore.common.api.vendor.MinecraftSoftwareVendorUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.stream.Collectors;

public class InfoCMD extends Command {

    private Drusk instance;

    protected InfoCMD(Drusk instance) {
        super("info");
        this.instance = instance;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (instance.getPermissionManager().lacks(sender, "admin")) {
            new BukkitMsgBuilder(instance.getLocalizationManager().get("error-permission")).send(sender);
            return true;
        }
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.isCalling("player")) {
            if (cmdHelper.argExists(1)) {
                Player selectedPlayer = Bukkit.getPlayer(args[1]);
                if (selectedPlayer == null) {
                    new BukkitMsgBuilder("&Not online").send(sender);
                    return true;
                }
                new BukkitMsgBuilder(
                        "" + "\n" +
                        "&eUsername: " + selectedPlayer.getName() + " \n" +
                                "&ePing: " + selectedPlayer.getPing() + " \n" +
                                "&eLP Primary Group: " + instance.getServer().getServicesManager().load(LuckPerms.class).getUserManager().getUser(selectedPlayer.getUniqueId()).getPrimaryGroup() + " \n" +
                                "&eIP: " + selectedPlayer.getAddress().getAddress() + " \n" +
                                "&eClient Brand: " + selectedPlayer.getClientBrandName() + " \n"
                ).send(sender);
                return true;
            }
        }
        if (cmdHelper.isCalling("delete")) {
            // Player data
            if (cmdHelper.argEquals(1, "playerdata")) {
                if (!cmdHelper.argExists(2)) {
                    return true;
                }
                if (!WorldUtils.exists(args[2])) {
                    return true;
                }
                World world = Bukkit.getWorld(args[2]);
                if (world == null) {
                    new BukkitMsgBuilder("&cWorld must be loaded!").send(sender);
                    return true;
                }
                new BukkitMsgBuilder("&cDeleting player data for world &e" + world.getName() + "&c and shutting down....").send(sender);
                for (Player player : instance.getServer().getOnlinePlayers()) {
                    player.kick(new BukkitMsgBuilder("Kicking players to remove data...").get());
                }
                WorldUtils.deletePlayerData(Bukkit.getWorld(args[2]));
                instance.getServer().shutdown();
            }
        }
        if (cmdHelper.isCalling("server")) {
            File folderFile = new File(instance.getDataFolder().getParent()).getAbsoluteFile();
            String folderName = folderFile.getParentFile().getName();

            new BukkitMsgBuilder("&7========== &3&l"+folderName+" &r&7==========")
                    .hover("&e"+ Icon.TRIANGLE_POINTING_RIGHT+" &7Click to copy path:&b\n"+folderFile.getParentFile().getAbsolutePath())
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, folderFile.getParentFile().getAbsolutePath()).send(sender);

            // Send vendor information
            if (MinecraftSoftwareVendorUtil.vendorExists(instance.getServer().getName())) {
                MinecraftSoftwareVendor vendor = MinecraftSoftwareVendorUtil.getVendorInformation(MinecraftSoftwareVendorUtil.getVendor(instance.getServer().getName()));
                Msg.send(sender, Msg.sendOneLine(
                        Msg.componentize("&3Type: &b"+vendor.getVendor().getVendor()),
                        formatValueInfo(vendor.getWebsite(), "Website"),
                        formatValueInfo(vendor.getDocumentation(), "Documentation"),
                        formatValueInfo(vendor.getBuilds(), "Builds"),
                        formatValueInfo(vendor.getDiscord(), "Discord"),
                        formatValueInfo(vendor.getSource(), "Source"),
                        formatValueInfo(vendor.getIssues(), "Issues")
                ));
            } else {
                Msg.send(sender, "&3Type: &b"+instance.getServer().getName());
            }
            Msg.send(sender, Msg.copyContent("&3Version: &b"+instance.getServer().getVersion()+" &7(" + instance.getServer().getBukkitVersion()+")","&7Copy to clipboard", instance.getServer().getVersion()));
            Msg.send(sender, Msg.suggestCommand("&3Whitelist: &b"+instance.getServer().isWhitelistEnforced(), "&3Whitelisted Players: \n&b"+instance.getServer().getWhitelistedPlayers().stream().map(OfflinePlayer::getName).collect(Collectors.joining(", ")),"/whitelist "));
            Msg.send(sender, Msg.copyContent("&3Port: &b"+instance.getServer().getPort(), "&7Click to copy",instance.getServer().getPort()+""));
            new BukkitMsgBuilder("Free Memory: " + (Runtime.getRuntime().freeMemory()/1048576) + "mb/" + (Runtime.getRuntime().totalMemory()/1048576)+"mb").send(sender);
            new BukkitMsgBuilder("Used Memory: " + ((Runtime.getRuntime().totalMemory()/1048576)-(Runtime.getRuntime().freeMemory()/1048576)) + "mb").send(sender);
        }
        // TODO check perms
        // server, status, player
        /*
        Msg.send(sender,
                "&3Implementation Name &b: " + instance.getServer().getName(),
                "&3Implementation Version &b: " + instance.getServer().getVersion() + " &7(" + instance.getServer().getBukkitVersion() + ")",
                "&3Players &b: " + instance.getServer().getOnlinePlayers().size()+" &7/ &b" + instance.getServer().getMaxPlayers(),
                "&3Port &b: " + instance.getServer().getPort(),
                "&3Server folder name &b: " + new File(instance.getDataFolder().getParent()).getAbsoluteFile().getParentFile().getName(),
                "&3End Enabled &b: " + instance.getServer().getAllowEnd(),
                "&3Nether Enabled &b: " + instance.getServer().getAllowNether()
                );

         */
        return false;
    }

    private Component formatValueInfo(String value, String valueName) {
        Component spacer = Msg.componentize("&7 | ");
        if (value != null) {
            return spacer.append(Msg.openWebsite("&b"+valueName, "&3Click to open &b"+value, value));
        }
        return null;
    }
}
