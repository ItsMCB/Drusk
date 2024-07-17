package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.PaginatedMenu;
import me.itsmcb.vexelcore.bukkit.api.menuv2.SkullBuilder;
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
        MenuV2 selectMenu = new PaginatedMenu("Select Cached Skin to Wear",36, player);
        instance.getCacheManager().getFromFile().forEach(cachedPlayer -> {
            selectMenu.addItem(new SkullBuilder(cachedPlayer).name("&d&l"+cachedPlayer.getName()).clickAction(e -> {
                PlayerUtils.setSkin(player, cachedPlayer.getPlayerSkin().getValue(),cachedPlayer.getPlayerSkin().getSignature());
                System.out.println(cachedPlayer.getPlayerSkin().getValue());
            })).clickCloseMenu(true);
        });
        instance.getMenuManager().open(selectMenu, player);
    }
}
