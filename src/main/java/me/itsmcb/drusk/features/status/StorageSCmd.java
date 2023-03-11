package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class StorageSCmd extends CustomCommand {

    private Drusk instance;

    public StorageSCmd(Drusk instance) {
        super("storage","View storage impact of files","drusk.admin");
        this.instance = instance;
    }

    @Override
    public List<String> getCompletions() {
        return BukkitUtils.getOnlinePlayerNames();
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        new BukkitMsgBuilder("&7=== &3&lStorage &7===").send(player);
        File serverFolder = Bukkit.getServer().getPluginsFolder().getAbsoluteFile().getParentFile().getAbsoluteFile();
        new BukkitMsgBuilder("&3Server Folder Size: " + FileUtils.getRecursiveFileSizeFormatted(serverFolder)).send(player);
        new BukkitMsgBuilder(serverFolder.getAbsolutePath()).send(player);
        new BukkitMsgBuilder("&3&lSystem").send(player);
        new BukkitMsgBuilder("&aAvailable Disk Space: &e" + (FileUtils.getAvailableDiskSpaceBytes(FileUtils.getOSRoot())/1073741824) + "GB").send(player);
        new BukkitMsgBuilder("&aTotal Disk Space: &e" + (FileUtils.getTotalDiskSpaceBytes(FileUtils.getOSRoot())/1073741824) + "GB").send(player);
    }
}
