package me.itsmcb.drusk.features.specialitems;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menuv2.ItemBuilder;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2ItemData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class SpecialItemsCmd extends CustomCommand {

    private Drusk instance;
    private NamespacedKey specialItemKey;

    public SpecialItemsCmd(Drusk instance, NamespacedKey key) {
        super("specialitems", "Obtain special items", "drusk.specialitems");
        this.instance = instance;
        this.specialItemKey = key;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        MenuV2 menu = new MenuV2("&d&lSpecial Items", InventoryType.CHEST,9);

        ItemBuilder barrier = new ItemBuilder(Material.BARRIER).name("&d&lBarrier");
        menu.addItem(new MenuV2Item(barrier).leftClickAction(event -> {
            player.getInventory().addItem(barrier.getCleanItemStack());
        }));

        ItemBuilder structureVoid = new ItemBuilder(Material.STRUCTURE_VOID).name("&d&lStructure Void");
        menu.addItem(new MenuV2Item(structureVoid).leftClickAction(event -> {
            player.getInventory().addItem(structureVoid.getCleanItemStack());
        }));

        ItemBuilder debugStick = new ItemBuilder(Material.DEBUG_STICK).name("&d&lDebug Stick");
        menu.addItem(new MenuV2Item(debugStick).leftClickAction(event -> {
            player.getInventory().addItem(debugStick.getCleanItemStack());
        }));

        // TODO Light block w/levels

        ItemBuilder itemFrame = new ItemBuilder(Material.ITEM_FRAME)
                .name("&d&lInvisible Item Frame")
                .addData(new MenuV2ItemData(specialItemKey,"invisible_item_frame"));
        menu.addItem(new MenuV2Item(itemFrame).leftClickAction(event -> {
            player.getInventory().addItem(itemFrame.getCleanItemStack());
        }));

        ItemBuilder knowledgeBook = new ItemBuilder(Material.KNOWLEDGE_BOOK)
                .name("&d&lKnowledge Book");
        menu.addItem(new MenuV2Item(knowledgeBook).leftClickAction(event -> {
            player.getInventory().addItem(knowledgeBook.getCleanItemStack());
        }));

        ItemBuilder endPortalFrame = new ItemBuilder(Material.END_PORTAL_FRAME)
                .name("&d&lEnd Portal")
                .addData(new MenuV2ItemData(specialItemKey,"end_portal"));
        menu.addItem(new MenuV2Item(endPortalFrame).leftClickAction(event -> {
            player.getInventory().addItem(endPortalFrame.getCleanItemStack());
        }));

        ItemBuilder obsidian = new ItemBuilder(Material.OBSIDIAN)
                .name("&d&lNether Portal X")
                .addData(new MenuV2ItemData(specialItemKey,"nether_portal_x"));
        menu.addItem(new MenuV2Item(obsidian).leftClickAction(event -> {
            player.getInventory().addItem(obsidian.getCleanItemStack());
        }));

        ItemBuilder obsidian2 = new ItemBuilder(Material.OBSIDIAN)
                .name("&d&lNether Portal Z")
                .addData(new MenuV2ItemData(specialItemKey,"nether_portal_z"));
        menu.addItem(new MenuV2Item(obsidian2).leftClickAction(event -> {
            player.getInventory().addItem(obsidian2.getCleanItemStack());
        }));

        instance.getMenuManager().open(menu, player);
    }
}
