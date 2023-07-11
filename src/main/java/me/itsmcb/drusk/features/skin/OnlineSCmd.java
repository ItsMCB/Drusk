package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.SkullBuilder;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.BukkitUtils;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class OnlineSCmd extends CustomCommand {

    private Drusk instance;

    public OnlineSCmd(Drusk instance) {
        super("online", "Copy a skin from another online player", "drusk.skin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                new BukkitMsgBuilder("&cPlayer is offline!").send(player);
                return;
            }
            PlayerUtils.copy(target,player);
            return;
        }
        if (Bukkit.getOnlinePlayers().size() == 1) {
            new BukkitMsgBuilder("&cThere aren't any other players online to copy.").send(player);
            return;
        }
        MenuV2 menu = new MenuV2("Select Player To Copy");
        // Filter command executor
        Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> onlinePlayer.getUniqueId() != player.getUniqueId()).forEach(onlinePlayer -> {
            menu.addItem(new SkullBuilder(onlinePlayer).name("&d&l"+onlinePlayer.getName()).lore(new BukkitMsgBuilder("&7Click to copy skin!").get()).leftClickAction(event -> {
                PlayerUtils.copy(onlinePlayer,player);
            }));
        });
        instance.getMenuManager().open(menu, player);
    }

    // TODO figure out why this completion doesn't work
    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }


}
