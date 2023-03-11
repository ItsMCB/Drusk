package me.itsmcb.drusk.features.head;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.utils.MojangUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Stream;

public class HeadCmd extends CustomCommand {

    private Drusk instance;

    public HeadCmd(Drusk instance) {
        super("head", "", "drusk.head");
        super.addParameter("[username]","Player's name");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                if (args.length < 1) {
                    meta.setOwningPlayer(player);
                } else {
                    meta.setOwningPlayer(Bukkit.getOfflinePlayer(args[0]));
                }
                head.setItemMeta(meta);
                player.getInventory().addItem(head);
            }
        }.runTaskAsynchronously(instance);
    }

    @Override
    public List<String> getAdditionalCompletions() {
        return Stream.concat(BukkitUtils.getOnlinePlayerNames().stream(), MojangUtils.MHFHeads().stream()).toList();
    }
}
