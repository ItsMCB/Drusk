package me.itsmcb.drusk.features.msg;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MsgCmd extends CustomCommand {
    public MsgCmd(Drusk instance) {
        super("msg", "Message other players", "drusk.msg");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (!(cmdHelper.argExists(0))) {
            new BukkitMsgBuilder("&cInclude player argument").send(player);
            return;
        }
        if (!BukkitUtils.isOnlinePlayer(args[0])) {
            new BukkitMsgBuilder("&cThat player isn't online.").send(player);
            return;
        }
        String message = cmdHelper.getStringOfArgsAfterIndex(0);
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        String formattedMessage = "&d&l"+player.getName()+" &8-> &d&l"+targetPlayer.getName()+"&8: &7"+message;
        // Cancel message if target player is sender
        if (player == targetPlayer) {
            new BukkitMsgBuilder("&cWhy are you trying to send yourself a private message?").send(player);
            return;
        }
        // Cancel message if it has no length
        if (message.length() == 0) {
            new BukkitMsgBuilder("&cMessage must have at least one character.").send(player);
            return;
        }
        TextComponent component = new BukkitMsgBuilder(formattedMessage).hover("&7Click to reply").clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg "+player.getName()+" ").get();

        // Send message back to sender
        player.sendMessage(component);

        // Send message to target
        targetPlayer.sendMessage(component);

        // Send staff message for moderation purposes
        Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> (onlinePlayer.hasPermission("drusk.mod") && onlinePlayer != targetPlayer && onlinePlayer != player)).toList().forEach(onlinePlayer -> {
            new BukkitMsgBuilder(formattedMessage).send(onlinePlayer);
        });
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }
}
