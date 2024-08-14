package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.text.Icon;
import me.itsmcb.vexelcore.common.api.utils.FileUtils;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class StorageSCmd extends CustomCommand {

    private Drusk instance;

    public StorageSCmd(Drusk instance) {
        super("storage","View the disk utilization of the server","drusk.admin");
        this.instance = instance;
    }

    @Override
    public List<String> getCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        File serverFolder = Bukkit.getServer().getPluginsFolder().getAbsoluteFile().getParentFile().getAbsoluteFile();
        new BukkitMsgBuilder("&8&m     &7&m     &8&m     &7&m     &r&8[ &3Storage &8]&7&m     &8&m     &7&m     &8&m     ").hover("&7Path: &3"+serverFolder.getAbsolutePath()).send(player);
        new BukkitMsgBuilder("&8╔ &l&3Server Folder").send(player);
        new BukkitMsgBuilder("&8╠═ &7Root: &3" + FileUtils.getRecursiveFileSizeFormatted(serverFolder))
                .hover("&9"+Icon.TRIANGLE_POINTING_RIGHT+" &7Click to copy path\n"+serverFolder.getAbsolutePath())
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,serverFolder.getAbsolutePath())
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7Plugins: &3" + FileUtils.getRecursiveFileSizeFormatted(Bukkit.getServer().getPluginsFolder())).send(player);
        new BukkitMsgBuilder("&8║").send(player);
        new BukkitMsgBuilder("&8╠ &l&3System").send(player);
        new BukkitMsgBuilder("&8╠═ &7Available Disk Space: &3" + FileUtils.getRecursiveFileSizeFormatted(FileUtils.getDiskAvailableBytes())).send(player);
        new BukkitMsgBuilder("&8╠═ &7Total Disk Space: &3" + FileUtils.getRecursiveFileSizeFormatted(FileUtils.getDiskTotalBytes())).send(player);
    }
}
