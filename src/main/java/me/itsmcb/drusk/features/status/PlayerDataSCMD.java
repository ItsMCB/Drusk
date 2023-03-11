package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.WorldUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerDataSCMD extends CustomCommand {

    private Drusk instance;

    public PlayerDataSCMD(Drusk instance) {
        super("playerdata","Manage playerdata","drusk.admin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (!cmdHelper.argExists(0)) {
            return;
        }
        if (!WorldUtils.exists(args[0])) {
            return;
        }
        World world = Bukkit.getWorld(args[0]);
        if (world == null) {
            new BukkitMsgBuilder("&cWorld must be loaded!").send(player);
            return;
        }
        new BukkitMsgBuilder("&cDeleting player data for world &e" + world.getName() + "&c and shutting down....").send(player);
        for (Player onlinePlayer : instance.getServer().getOnlinePlayers()) {
            onlinePlayer.kick(new BukkitMsgBuilder("Kicking players to remove data...").get());
        }
        WorldUtils.deletePlayerData(Bukkit.getWorld(args[0]));
        instance.getServer().shutdown();
    }
}
