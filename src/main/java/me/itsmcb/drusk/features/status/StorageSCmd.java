package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.utils.FileUtils;
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
        new BukkitMsgBuilder("&8--=== &b&lStorage &8===--").hover("&7Path: &3"+serverFolder.getAbsolutePath()).send(player);
        new BukkitMsgBuilder("&8&l- &3Folders").send(player);
        new BukkitMsgBuilder("&7Root: &3" + FileUtils.getRecursiveFileSizeFormatted(serverFolder)).send(player);
        new BukkitMsgBuilder("&7Plugins: &3" + FileUtils.getRecursiveFileSizeFormatted(Bukkit.getServer().getPluginsFolder())).send(player);
        new BukkitMsgBuilder("&8&l- &3System").send(player);
        long totalGB = FileUtils.getTotalDiskSpaceBytes(FileUtils.getOSRoot())/1073741824;
        long availableGB = FileUtils.getAvailableDiskSpaceBytes(FileUtils.getOSRoot())/1073741824;
        new BukkitMsgBuilder("&7Available Disk Space: &3" + availableGB + " GB").send(player);
        new BukkitMsgBuilder("&7Total Disk Space: &3" + totalGB + " GB").send(player);
    }
}
