package me.itsmcb.drusk.features.weext;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.PaginatedMenu;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.voyage.api.VoyageAPI;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.Arrays;
import java.util.List;

public class WorldEditMenuCmd extends CustomCommand {

    private Drusk instance;

    public WorldEditMenuCmd(Drusk instance) {
        super("wm", "", "wip");
        this.instance = instance;
    }

    private void runWECmd(String cmd, Player player) {
        new BukkitMsgBuilder("&7Running command: &3/"+cmd).hover("&7Click to copy command").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/"+cmd).send(player);
        player.performCommand(cmd);
    }

    private void openBiomeMenu(Player player) {
        PaginatedMenu biomeMenu = new PaginatedMenu("&3&nBiome Selector",54,player).clickCloseMenu(true);
        List<Biome> validBiomes = Arrays.stream(Biome.values()).filter(b -> !b.getKey().getKey().equals("custom")).toList();
        for (Biome biome :validBiomes) {
            biomeMenu.addItem(new MenuV2Item(Material.PAPER).name("&d&l"+biome.getKey().getKey()).clickAction(e -> {
                runWECmd("/setbiome "+biome.getKey().getKey(),player);
                // Teleport player away and then back so that their client refreshes the biome
                Location loc = player.getLocation();
                VoyageAPI.evacuate(player);
                player.teleport(loc);
            }));
        }
        instance.getMenuManager().open(biomeMenu,player);
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        MenuV2 main = new MenuV2("&3&nWorld Edit Quick Menu (WIP)", InventoryType.DROPPER,9).clickCloseMenu(true)
                .addItem(new MenuV2Item(Material.SNOW_BLOCK).name("&dSet Biome").clickAction(e -> {
                    openBiomeMenu(player);
                }))
                .addItem(new MenuV2Item(Material.GRASS).name("&dApply Grass and Ferns").clickAction(e -> {
                    runWECmd("/re \"air >2\" #simplex[4][grass,fern,air,air,air]",player);
                }));
        instance.getMenuManager().open(main,player);
    }
}
