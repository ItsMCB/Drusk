package me.itsmcb.drusk.features.skin.copy;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.VexelCoreBukkitAPI;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menu.MenuButton;
import me.itsmcb.vexelcore.bukkit.api.menu.MenuRowSize;
import me.itsmcb.vexelcore.bukkit.api.menu.PaginatedMenu;
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
        PaginatedMenu menu = new PaginatedMenu(MenuRowSize.FIVE,"&7Select Player to Copy");
        // Filter command executor
        Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> onlinePlayer.getUniqueId() != player.getUniqueId()).forEach(onlinePlayer -> {
            VexelCoreBukkitAPI.getCacheManager().getCachedPlayer(onlinePlayer.getUniqueId()).thenAccept(p -> {
                menu.addButton(new MenuButton(p).name("&d"+p.getUsername()).addLore("&7Click to wear").click(e -> {
                    PlayerUtils.copy(onlinePlayer,player);
                }));
            });
        });
        VexelCoreBukkitAPI.getMenuManager().open(menu,player);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return BukkitUtils.getOnlinePlayerNames();
    }


}
