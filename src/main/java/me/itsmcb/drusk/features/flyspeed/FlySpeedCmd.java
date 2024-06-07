package me.itsmcb.drusk.features.flyspeed;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FlySpeedCmd extends CustomCommand {
    public FlySpeedCmd(Drusk instance, String commandName) {
        super(commandName, "Set flight speed.", "drusk.flyspeed");
        addParameter("[number 0.0 - 10.0]", "Speed");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        // Determine if the speed input is valid
        float speed = 1f;
        if (!args[0].equalsIgnoreCase("reset")) {
            try {
                speed = Float.parseFloat(args[0]);
            } catch (NumberFormatException e) {
                new BukkitMsgBuilder("&cInvalid input number!").send(player);
                help(player);
                return;
            }
        }
        // Determine if speed is possible
        if (speed > 10f || speed < -10f) {
            new BukkitMsgBuilder("&cFlight speed must be under 10!").send(player);
            return;
        }
        // Apply speed to target
        Player target = player;
        if (cmdHelper.argExists(1)) {
            if (BukkitUtils.isOnlinePlayer(args[1])) {
                target = Bukkit.getPlayer(args[1]);
            } else {
                new BukkitMsgBuilder("&cPlayer isn't online!").send(player);
            }
        }
        target.setFlySpeed(speed == 0.0f ? 0.0f : speed / 10.0f);
        new BukkitMsgBuilder("&7Set fly speed of &a" + target.getName() + "&7 to &a" + speed).send(player);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return List.of("reset","1","2","3","4","5","6","7","8","9","10");
    }
}
