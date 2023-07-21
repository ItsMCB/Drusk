package me.itsmcb.drusk.features.spawn;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.WorldUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCMD extends CustomCommand {

    private Drusk instance;

    public SpawnCMD(@NotNull Drusk instance) {
        super("spawn","Go to spawn","");
        this.instance = instance;
        registerSubCommand(new SpawnSetSCmd(instance));
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        if (!instance.getMainConfig().get().is("spawn.location", Location.class)) {
            new BukkitMsgBuilder("&cSpawn location not yet set!").send(player);
            return;
        }
        // TODO fix world check on 1.20.1
        Location location = (Location) instance.getMainConfig().get().get("spawn.location", Location.class);
        if (!WorldUtils.isLoaded(location.getWorld().getName())) {
            new BukkitMsgBuilder("&cWorld not found! Is it loaded?").send(player);
            return;
        }
        player.teleport(location);
        new BukkitMsgBuilder("&7Teleported to spawn").send(player);
    }
}
