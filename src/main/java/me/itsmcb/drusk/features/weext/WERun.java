package me.itsmcb.drusk.features.weext;

import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;

public class WERun {

    public static void runWECmd(String cmd, Player player) {
        TextComponent ranCmd = new BukkitMsgBuilder("&7Running command: &3/"+cmd+" ")
                .hover("&7Click to copy command")
                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/"+cmd).get();
        TextComponent undo = new BukkitMsgBuilder("&8[&c//undo&8]")
                .hover("&7Click to run //undo")
                .clickEvent(ClickEvent.Action.RUN_COMMAND,"/undo").get();
        player.sendMessage(Component.empty().append(ranCmd).append(undo));
        player.performCommand(cmd);
    }

}
