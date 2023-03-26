package me.itsmcb.drusk.features.drusk;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import org.bukkit.entity.Player;

public class ReloadSCmd extends CustomCommand {

    private Drusk instance;

    public ReloadSCmd(Drusk instance) {
        super("reload", "Reload configurations", "drusk.admin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        instance.resetManagers();
        new BukkitMsgBuilder("&aReload successful.").send(player);
    }
}
