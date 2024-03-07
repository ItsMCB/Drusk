package me.itsmcb.drusk.features.fun;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SmiteCmd extends CustomCommand {

    private Drusk instance;

    public SmiteCmd(Drusk instance) {
        super("smite", "Summon lighting upon a player.", "drusk.smite");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.argNotExists(0)) {
            error(player);
            return;
        }
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            System.out.println(targetPlayer == null);
            System.out.println(!targetPlayer.isOnline());
            error(player);
            return;
        }
        AtomicInteger strikes = new AtomicInteger(1);
        if (cmdHelper.argExists(1) && cmdHelper.isInt(1)) {
            strikes.set(Integer.parseInt(args[1]));
        }
        new BukkitMsgBuilder("&d"+targetPlayer.getName()+" &7is feeling your wrath!").send(player);
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (strikes.get() > 0) {
                    targetPlayer.getWorld().strikeLightningEffect(targetPlayer.getLocation());
                    strikes.set(strikes.get()-1);
                } else {
                    cancel();
                }
            }
        };
        runnable.runTaskTimer(instance, 0L, 2L);
    }

    private void error(Player player) {
        new BukkitMsgBuilder("&cPlease provide an online player target argument.").send(player);
    }

    @Override
    public List<String> getCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
