package me.itsmcb.drusk.features.specialitems;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2ItemData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Snowable;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

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
        MenuV2 menu = new MenuV2("&7ꜱᴘᴇᴄɪᴀʟ ɪᴛᴇᴍꜱ", InventoryType.CHEST,18);

        MenuV2Item barrier = new MenuV2Item(Material.BARRIER).name("&d&lBarrier");
        menu.addItem(barrier.leftClickAction(event -> {
            player.getInventory().addItem(barrier.getCleanItemStack());
        }));

        MenuV2Item structureVoid = new MenuV2Item(Material.STRUCTURE_VOID).name("&d&lStructure Void");
        menu.addItem(structureVoid.leftClickAction(event -> {
            player.getInventory().addItem(structureVoid.getCleanItemStack());
        }));

        MenuV2Item debugStick = new MenuV2Item(Material.DEBUG_STICK).name("&d&lDebug Stick");
        menu.addItem(debugStick.leftClickAction(event -> {
            player.getInventory().addItem(debugStick.getCleanItemStack());
        }));

        MenuV2Item itemFrame = new MenuV2Item(Material.ITEM_FRAME)
                .name("&d&lInvisible Item Frame")
                .addData(new MenuV2ItemData(specialItemKey,"invisible_item_frame"));
        menu.addItem(itemFrame.leftClickAction(event -> {
            player.getInventory().addItem(itemFrame.getCleanItemStack());
        }));

        MenuV2Item knowledgeBook = new MenuV2Item(Material.KNOWLEDGE_BOOK)
                .name("&d&lKnowledge Book");
        menu.addItem(knowledgeBook.leftClickAction(event -> {
            player.getInventory().addItem(knowledgeBook.getCleanItemStack());
        }));

        MenuV2Item furnace = new MenuV2Item(Material.FURNACE)
                .name("&d&lLit Furnace")
                .addData(new MenuV2ItemData(specialItemKey,"lit_furnace"));
        menu.addItem(furnace.leftClickAction(event -> {
            player.getInventory().addItem(furnace.getCleanItemStack());
        }));

        MenuV2Item smoker = new MenuV2Item(Material.SMOKER)
                .name("&d&lLit Smoker")
                .addData(new MenuV2ItemData(specialItemKey,"lit_smoker"));
        menu.addItem(smoker.leftClickAction(event -> {
            player.getInventory().addItem(smoker.getCleanItemStack());
        }));

        MenuV2Item campfire = new MenuV2Item(Material.CAMPFIRE)
                .name("&d&lLit Campfire")
                .addData(new MenuV2ItemData(specialItemKey,"lit_campfire"));
        menu.addItem(campfire.leftClickAction(event -> {
            player.getInventory().addItem(campfire.getCleanItemStack());
        }));

        MenuV2Item endPortalFrame = new MenuV2Item(Material.END_PORTAL_FRAME)
                .name("&d&lEnd Portal")
                .addData(new MenuV2ItemData(specialItemKey,"end_portal"));
        menu.addItem(endPortalFrame.leftClickAction(event -> {
            player.getInventory().addItem(endPortalFrame.getCleanItemStack());
        }));

        MenuV2Item obsidian = new MenuV2Item(Material.OBSIDIAN)
                .name("&d&lNether Portal X")
                .addData(new MenuV2ItemData(specialItemKey,"nether_portal_x"));
        menu.addItem(obsidian.leftClickAction(event -> {
            player.getInventory().addItem(obsidian.getCleanItemStack());
        }));

        MenuV2Item obsidian2 = new MenuV2Item(Material.OBSIDIAN)
                .name("&d&lNether Portal Z")
                .addData(new MenuV2ItemData(specialItemKey,"nether_portal_z"));
        menu.addItem(obsidian2.leftClickAction(event -> {
            player.getInventory().addItem(obsidian2.getCleanItemStack());
        }));

        MenuV2Item light = new MenuV2Item(Material.LIGHT)
                .name("&d&lLight 15")
                .addData(new MenuV2ItemData(specialItemKey,"light"));
        menu.addItem(light.leftClickAction(event -> {
            player.getInventory().addItem(light.getCleanItemStack());
        }));

        // Snowy Grass
        MenuV2Item snowyGrass = new MenuV2Item(Material.GRASS_BLOCK).name("&d&lSnowy Grass");
        ItemMeta snowyGrassMeta = snowyGrass.getItemMeta();
        BlockDataMeta snowyBlockMeta = (BlockDataMeta) snowyGrassMeta;
        BlockData blockData = snowyGrass.getType().createBlockData();
        ((Snowable) blockData).setSnowy(true);
        snowyBlockMeta.setBlockData(blockData);
        snowyGrass.setItemMeta(snowyBlockMeta);
        menu.addItem(snowyGrass.leftClickAction(event -> {
            player.getInventory().addItem(snowyGrass.getCleanItemStack());
        }));

        // Infinite Night Vision
        MenuV2Item nightVisionPotion = new MenuV2Item(Material.POTION)
                .name("&d&lInfinite Night Vision Potion")
                .addLore("&7Drink milk to remove all effects!");
        PotionMeta nightVisionPotionMeta = (PotionMeta) nightVisionPotion.getItemMeta();
        nightVisionPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false), true);
        //nightVisionPotionMeta.setBasePotionType(PotionType.LONG_NIGHT_VISION);
        nightVisionPotion.setItemMeta(nightVisionPotionMeta);
        menu.addItem(nightVisionPotion.leftClickAction(event -> {
            player.getInventory().addItem(nightVisionPotion.getCleanItemStack());
        }));

        /*
        // Arctic Fox
        MenuV2Item arcticFoxSpawnEgg = new MenuV2Item(Material.FOX_SPAWN_EGG).name("&d&lArctic Fox Spawn Egg")
                .addData(new MenuV2ItemData(specialItemKey,"fox_snow"));
        menu.addItem(arcticFoxSpawnEgg.leftClickAction(event -> {
            player.getInventory().addItem(arcticFoxSpawnEgg.getCleanItemStack());
        }));
         */

        instance.getMenuManager().open(menu, player);
    }
}
