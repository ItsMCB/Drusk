package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.VexelCoreBukkitAPI;
import me.itsmcb.vexelcore.bukkit.api.cache.exceptions.DataRequestFailure;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menu.MenuButton;
import me.itsmcb.vexelcore.bukkit.api.menu.MenuRowSize;
import me.itsmcb.vexelcore.bukkit.api.menu.PaginatedMenu;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import org.bukkit.entity.Player;

public class CachedSCmd extends CustomCommand {

    private Drusk instance;

    public CachedSCmd(Drusk instance) {
        super("cached", "Select a cached skin", "group.adin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        PaginatedMenu selectMenu = new PaginatedMenu(MenuRowSize.FOUR,"Select Cached Skin to Wear");
        try {
            VexelCoreBukkitAPI.getCacheManager().getAllPlayers().forEach(p -> {
                selectMenu.addButton(new MenuButton(p).name("&d"+p.getUsername()).addLore("&7Click to copy").click(e -> {
                    PlayerUtils.setSkin(player,p.getPlayerSkinData().getTexture(),p.getPlayerSkinData().getSignature());
                }));
            });
            VexelCoreBukkitAPI.getMenuManager().open(selectMenu,player);
        } catch (DataRequestFailure e) {
            throw new RuntimeException(e);
        }
    }
}
