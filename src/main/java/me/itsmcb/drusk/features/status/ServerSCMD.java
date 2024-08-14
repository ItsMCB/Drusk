package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.vendor.MinecraftSoftwareVendor;
import me.itsmcb.vexelcore.common.api.vendor.MinecraftSoftwareVendorUtil;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;

public class ServerSCMD extends CustomCommand {

    private Drusk instance;

    public ServerSCMD(Drusk instance) {
        super("server","View status of the server","drusk.admin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        new BukkitMsgBuilder("&8&m     &7&m     &8&m     &7&m     &r&8[ &3Server Status &8]&7&m     &8&m     &7&m     &8&m     ").send(player);
        new BukkitMsgBuilder("&8╠═ &7IP: &3"+instance.getServer().getIp())
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,instance.getServer().getIp())
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7Port: &3"+instance.getServer().getPort())
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,instance.getServer().getPort()+"")
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7Slots: &3"+instance.getServer().getOnlinePlayers().size()+"&7/&3" + instance.getServer().getMaxPlayers()).send(player);
        new BukkitMsgBuilder("&8╠═ &7Nether: &3"+(instance.getServer().getAllowNether() ? "Enabled" : "Disabled")).send(player);
        new BukkitMsgBuilder("&8╠═ &7The End: &3"+(instance.getServer().getAllowEnd() ? "Enabled" : "Disabled")).send(player);
        new BukkitMsgBuilder("&8╠═ &7Version: &3"+instance.getServer().getVersion())
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,instance.getServer().getVersion())
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7View Distance: &3"+instance.getServer().getViewDistance())
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,instance.getServer().getViewDistance()+"")
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7Default Game Mode: &3"+instance.getServer().getDefaultGameMode().name())
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,instance.getServer().getDefaultGameMode().name())
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7Whitelist: &3"+(instance.getServer().isWhitelistEnforced() ? "Enabled" : "Disabled")).send(player);
        instance.getServer().getWhitelistedPlayers().forEach(wlp -> {
            new BukkitMsgBuilder("&8╠══ &3"+wlp.getName()).hover("&7Click to copy").clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,wlp.getName()).send(player);
        });
        // Send vendor information
        new BukkitMsgBuilder("&8║").send(player);
        new BukkitMsgBuilder("&8╠ &l&3Vendor Information").send(player);
        if (MinecraftSoftwareVendorUtil.vendorExists(instance.getServer().getName())) {
            MinecraftSoftwareVendor vendor = MinecraftSoftwareVendorUtil.getVendorInformation(MinecraftSoftwareVendorUtil.getVendor(instance.getServer().getName()));
            new BukkitMsgBuilder("&8╠═ &7Name: &3"+instance.getServer().getName()).send(player);
            new BukkitMsgBuilder("&8╠═ &7Website: &3"+vendor.getWebsite())
                    .hover("&7Click to open website")
                    .clickEvent(ClickEvent.Action.OPEN_URL, vendor.getWebsite())
                    .send(player);
            new BukkitMsgBuilder("&8╠═ &7Documentation: &3"+vendor.getDocumentation())
                    .hover("&7Click to open documentation website")
                    .clickEvent(ClickEvent.Action.OPEN_URL, vendor.getDocumentation())
                    .send(player);
            new BukkitMsgBuilder("&8╠═ &7Builds (Downloads): &3"+vendor.getBuilds())
                    .hover("&7Click to open builds website")
                    .clickEvent(ClickEvent.Action.OPEN_URL, vendor.getBuilds())
                    .send(player);
            new BukkitMsgBuilder("&8╠═ &7Discord (Support): &3"+vendor.getDiscord())
                    .hover("&7Click to open Discord")
                    .clickEvent(ClickEvent.Action.OPEN_URL, vendor.getDiscord())
                    .send(player);
            new BukkitMsgBuilder("&8╠═ &7Source: &3"+vendor.getSource())
                    .hover("&7Click to open source code")
                    .clickEvent(ClickEvent.Action.OPEN_URL, vendor.getSource())
                    .send(player);
            new BukkitMsgBuilder("&8╠═ &7Report Issue: &3"+vendor.getIssues())
                    .hover("&7Click to open issue tracker")
                    .clickEvent(ClickEvent.Action.OPEN_URL, vendor.getIssues())
                    .send(player);
        } else {
            new BukkitMsgBuilder("&8╠═ &7Type: &3"+instance.getServer().getName()).send(player);
        }
    }
}
