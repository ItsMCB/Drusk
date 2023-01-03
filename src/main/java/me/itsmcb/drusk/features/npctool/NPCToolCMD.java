package me.itsmcb.drusk.features.npctool;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.experience.AudioResponse;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.Msg;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NPCToolCMD extends Command {

    private Drusk instance;

    public NPCToolCMD(@NotNull Drusk instance) {
        super("npctool");
        this.instance = instance;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (instance.getPermissionManager().lacks(sender, "admin")) {
            new BukkitMsgBuilder(instance.getLocalizationManager().get("error-permission")).send(sender);
            return true;
        }
        if (!(sender instanceof Player)) {
            // TODO error message
            return true;
        }
        Player player = (Player) sender;
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.isCalling("create")) {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
            Location playerLocation = player.getLocation().toCenterLocation();
            if (cmdHelper.argExists(1)) {
                if (args[1].equalsIgnoreCase("south")) {
                    playerLocation.setYaw(0);
                }
                if (args[1].equalsIgnoreCase("west")) {
                    playerLocation.setYaw(98);
                }
                if (args[1].equalsIgnoreCase("north")) {
                    playerLocation.setYaw(180);
                }
                if (args[1].equalsIgnoreCase("east")) {
                    playerLocation.setYaw(270);
                }
            }
            playerLocation.setPitch(0);
            npc.spawn(playerLocation);
            Msg.sendResponsive(AudioResponse.SUCCESS, sender, "&aNPC Created! - #" + npc.getUniqueId());
            return true;
        }
        new BukkitMsgBuilder("Options: /npctool create - Create centered player NPC").send(sender);
        // TODO send usage
        return false;
    }
}
