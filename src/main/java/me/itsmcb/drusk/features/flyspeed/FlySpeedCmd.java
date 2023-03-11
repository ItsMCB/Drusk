package me.itsmcb.drusk.features.flyspeed;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class FlySpeedCmd extends CustomCommand {
    public FlySpeedCmd(Drusk instance, String commandName) {
        super(commandName, "Take flight!", "drusk.flyspeed");
        addParameter("[number 1-10]", "Speed");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.isInt(0)) {
            float newSpeed = 0;
            switch (Integer.parseInt(args[0])) {
                case 1 -> newSpeed = 0.1F;
                case 2 -> newSpeed = 0.2F;
                case 3 -> newSpeed = 0.3F;
                case 4 -> newSpeed = 0.4F;
                case 5 -> newSpeed = 0.5F;
                case 6 -> newSpeed = 0.6F;
                case 7 -> newSpeed = 0.7F;
                case 8 -> newSpeed = 0.8F;
                case 9 -> newSpeed = 0.9F;
                default -> newSpeed = 1F;
            }
            Player target = player;
            if (cmdHelper.argExists(1)) {
                if (BukkitUtils.isOnlinePlayer(args[1])) {
                    target = Bukkit.getPlayer(args[1]);
                } else {
                    new BukkitMsgBuilder("&cPlayer isn't online!").send(player);
                }
            }
            target.setFlySpeed(newSpeed);
            new BukkitMsgBuilder("&7Set fly speed of &a" + target.getName() + "&7 to &a" + args[0]);
            return;
        }
        player.sendMessage(help());
    }

    @Override
    public List<String> getAdditionalCompletions() {
        return List.of("1","2","3","4","5","6","7","8","9","10");
    }
}
