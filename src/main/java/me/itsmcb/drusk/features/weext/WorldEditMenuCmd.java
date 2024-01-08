package me.itsmcb.drusk.features.weext;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.conversation.InputPrefix;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.PaginatedMenu;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.voyage.api.VoyageAPI;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.Arrays;
import java.util.List;

public class WorldEditMenuCmd extends CustomCommand {

    private Drusk instance;

    public WorldEditMenuCmd(Drusk instance) {
        super("wm", "", "drusk.wm");
        this.instance = instance;
    }


    private void openBiomeMenu(Player player) {
        PaginatedMenu biomeMenu = new PaginatedMenu("&3&nBiome Selector",54,player).clickCloseMenu(true);
        List<Biome> validBiomes = Arrays.stream(Biome.values()).filter(b -> !b.getKey().getKey().equals("custom")).toList();
        for (Biome biome :validBiomes) {
            biomeMenu.addItem(new MenuV2Item(Material.PAPER).name("&d&l"+biome.getKey().getKey()).clickAction(e -> {
                WERun.runWECmd("/setbiome "+biome.getKey().getKey(),player);
                // Teleport player away and then back so that their client refreshes the biome
                Location loc = player.getLocation();
                VoyageAPI.evacuate(player);
                player.teleport(loc);
            }));
        }
        instance.getMenuManager().open(biomeMenu,player);
    }

    private void openFloodMenu(Player player) {
        MenuV2 floodMenu = new MenuV2("&3&nLiquid Manager",InventoryType.CHEST,9).clickCloseMenu(true);
        Conversation c = new ConversationFactory(instance)
                .withFirstPrompt(new RadiusPrompt())
                .withEscapeSequence("exit")
                .withTimeout(60)
                .withPrefix(new InputPrefix())
                .withLocalEcho(false)
                .buildConversation(player);

        floodMenu.addItem(new MenuV2Item(Material.WATER_BUCKET).name("&dWater").slot(3).clickAction(e -> {
            c.getContext().setSessionData("cmd","/fill water ");
            c.begin();
        }));
        floodMenu.addItem(new MenuV2Item(Material.LAVA_BUCKET).name("&dLava").slot(4).clickAction(e -> {
            c.getContext().setSessionData("cmd","/fill lava ");
            c.begin();
        }));
        floodMenu.addItem(new MenuV2Item(Material.BUCKET).name("&dDrain").addLore("&7Remove liquids").slot(5).clickAction(e -> {
            c.getContext().setSessionData("cmd","/drain ");
            c.begin();
        }));
        instance.getMenuManager().open(floodMenu,player);
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        MenuV2 main = new MenuV2("&3&nWorld Edit Menu", InventoryType.DROPPER,9).clickCloseMenu(true)
                .addItem(new MenuV2Item(Material.SNOW_BLOCK).name("&dSet Biome").clickAction(e -> {
                    openBiomeMenu(player);
                }))
                .addItem(new MenuV2Item(Material.GRASS).name("&dApply Grass and Ferns")
                        .addLore("&7For this to work, ensure your selection includes air above it.")
                        .clickAction(e -> {
                    WERun.runWECmd("/re \"air >2\" #simplex[4][tall_grass,fern,air,air,air]",player);
                }))
                .addItem(new MenuV2Item(Material.WATER_BUCKET).name("&dFill with Liquid").addLore("Flood radius with water, lava, or drain it.").clickAction(e -> {
                    openFloodMenu(player);
                }));
        instance.getMenuManager().open(main,player);
    }
}
