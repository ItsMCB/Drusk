package me.itsmcb.drusk.features.specialitems;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.VexelCoreBukkitAPI;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menu.ItemData;
import me.itsmcb.vexelcore.bukkit.api.menu.Menu;
import me.itsmcb.vexelcore.bukkit.api.menu.MenuButton;
import me.itsmcb.vexelcore.bukkit.api.menu.MenuRowSize;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2ItemData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Snowable;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        Menu menu = new Menu(MenuRowSize.THREE,"&7ꜱᴘᴇᴄɪᴀʟ ɪᴛᴇᴍꜱ");
        // Barrier
        menu.addButton(new MenuButton(Material.BARRIER).name("&dBarrier").click(e -> {
            player.getInventory().addItem(new ItemStack(Material.BARRIER));
        }));
        // Structure Void
        menu.addButton(new MenuButton(Material.STRUCTURE_VOID).name("&dStructure Void").click(e -> {
            player.getInventory().addItem(new ItemStack(Material.STRUCTURE_VOID));
        }));
        // Debug Stick
        menu.addButton(new MenuButton(Material.DEBUG_STICK).name("&dDebug Stick").click(e -> {
            player.getInventory().addItem(new ItemStack(Material.DEBUG_STICK));
        }));
        // Invisible Item Frame
        MenuButton invisibleItemFrame = new MenuButton(Material.ITEM_FRAME)
                .name("&dInvisible Item Frame")
                .addPersistantItemData(new ItemData(specialItemKey,"glow"));
        menu.addButton(invisibleItemFrame.click(e -> {
            player.getInventory().addItem(invisibleItemFrame.getCleanItemStack());
        }));
        // Invisible Glow Item Frame
        MenuButton invisibleGlowItemFrame = new MenuButton(Material.GLOW_ITEM_FRAME)
                .name("&dInvisible Glow Item Frame")
                .addPersistantItemData(new ItemData(specialItemKey,"glow"));
        menu.addButton(invisibleGlowItemFrame.click(e -> {
            player.getInventory().addItem(invisibleGlowItemFrame.getCleanItemStack());
        }));
        // Knowledge Book
        menu.addButton(new MenuButton(Material.KNOWLEDGE_BOOK).name("&dKnowledge Book").click(e -> {
            player.getInventory().addItem(new ItemStack(Material.KNOWLEDGE_BOOK));
        }));
        // Lit Furnace
        MenuButton litFurnace = new MenuButton(Material.FURNACE)
                .name("&dLit Furnace")
                .addPersistantItemData(new ItemData(specialItemKey,"lit_furnace"));
        menu.addButton(litFurnace.click(e -> {
            player.getInventory().addItem(litFurnace.getCleanItemStack());
        }));
        // Lit Blast Furnace
        MenuButton litBlastFurnace = new MenuButton(Material.BLAST_FURNACE)
                .name("&dLit Blast Furnace")
                .addPersistantItemData(new ItemData(specialItemKey,"lit_furnace"));
        menu.addButton(litBlastFurnace.click(e -> {
            player.getInventory().addItem(litBlastFurnace.getCleanItemStack());
        }));
        // Lit Smoker
        MenuButton litSmoker = new MenuButton(Material.SMOKER)
                .name("&dLit Smoker")
                .addPersistantItemData(new ItemData(specialItemKey,"lit_furnace"));
        menu.addButton(litSmoker.click(e -> {
            player.getInventory().addItem(litSmoker.getCleanItemStack());
        }));
        // Campfire
        MenuButton unlitCampfire = new MenuButton(Material.CAMPFIRE)
                .name("&dUnlit Campfire")
                .addPersistantItemData(new ItemData(specialItemKey,"unlit_campfire"));
        menu.addButton(unlitCampfire.click(e -> {
            player.getInventory().addItem(unlitCampfire.getCleanItemStack());
        }));
        // Trapdoor
        MenuButton openTrapdoor = new MenuButton(Material.IRON_TRAPDOOR)
                .name("&dOpen Trapdoor")
                .addLore("&7Right-click to modify")
                .addPersistantItemData(new ItemData(specialItemKey,"open_trapdoor"));
        menu.addButton(openTrapdoor.click(e -> {
            player.getInventory().addItem(openTrapdoor.getCleanItemStack());
        }));
        // End Portal
        MenuButton endPortal = new MenuButton(Material.END_PORTAL_FRAME)
                .name("&dEnd Portal")
                .addPersistantItemData(new ItemData(specialItemKey,"end_portal"));
        menu.addButton(endPortal.click(e -> {
            player.getInventory().addItem(endPortal.getCleanItemStack());
        }));
        // End Gateway
        MenuButton endGateway = new MenuButton(Material.END_STONE)
                .name("&dEnd Gateway")
                .addPersistantItemData(new ItemData(specialItemKey,"end_gateway"));
        menu.addButton(endGateway.click(e -> {
            player.getInventory().addItem(endGateway.getCleanItemStack());
        }));
        // Nether Portal X
        MenuButton netherPortalX = new MenuButton(Material.OBSIDIAN)
                .name("&dNether Portal X")
                .addPersistantItemData(new ItemData(specialItemKey,"nether_portal_x"));
        menu.addButton(netherPortalX.click(e -> {
            player.getInventory().addItem(netherPortalX.getCleanItemStack());
        }));
        // Nether Portal Z
        MenuButton netherPortalZ = new MenuButton(Material.OBSIDIAN)
                .name("&dNether Portal Z")
                .addPersistantItemData(new ItemData(specialItemKey,"nether_portal_z"));
        menu.addButton(netherPortalZ.click(e -> {
            player.getInventory().addItem(netherPortalZ.getCleanItemStack());
        }));
        // Light
        MenuButton light = new MenuButton(Material.LIGHT)
                .name("&dLight")
                .addLore("&7Right-click to modify light level")
                .addPersistantItemData(new ItemData(specialItemKey,"light"));
        menu.addButton(light.click(e -> {
            player.getInventory().addItem(light.getCleanItemStack());
        }));
        // Snowy Grass
        ItemStack snowyGrassStack = new MenuV2Item(Material.GRASS_BLOCK).name("&d&lSnowy Grass");
        ItemMeta snowyGrassMeta = snowyGrassStack.getItemMeta();
        BlockDataMeta snowyBlockMeta = (BlockDataMeta) snowyGrassMeta;
        BlockData blockData = snowyGrassStack.getType().createBlockData();
        ((Snowable) blockData).setSnowy(true);
        snowyBlockMeta.setBlockData(blockData);
        snowyGrassStack.setItemMeta(snowyBlockMeta);

        MenuButton snowyGrass = new MenuButton(snowyGrassStack).name("&dSnowy Grass");
        menu.addButton(snowyGrass.click(e -> {
            player.getInventory().addItem(snowyGrass.getCleanItemStack());
        }));
        // Infinite Night Vision
        ItemStack nightVisionPotionStack = new ItemStack(Material.POTION);
        PotionMeta nightVisionPotionMeta = (PotionMeta) nightVisionPotionStack.getItemMeta();
        nightVisionPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false), true);
        nightVisionPotionStack.setItemMeta(nightVisionPotionMeta);

        MenuButton nightVisionPotion = new MenuButton(nightVisionPotionStack)
                .name("&d&lInfinite Night Vision Potion")
                .addLore("&7Drink milk to remove all effects!");
        menu.addButton(nightVisionPotion.click(e -> {
            player.getInventory().addItem(nightVisionPotion.getCleanItemStack());
        }));
        // Arctic Fox
        ItemStack foxSpawnEggStack = new ItemStack(Material.FOX_SPAWN_EGG);
        SpawnEggMeta foxSpawnEggMeta = (SpawnEggMeta) foxSpawnEggStack.getItemMeta();
        foxSpawnEggMeta.setSpawnedEntity(Bukkit.getEntityFactory().createEntitySnapshot("{ \"id\": \"minecraft:fox\", \"Type\": \"snow\" }"));
        foxSpawnEggStack.setItemMeta(foxSpawnEggMeta);

        MenuButton foxSpawnEgg = new MenuButton(foxSpawnEggStack).name("&d&lArctic Fox");
        menu.addButton(foxSpawnEgg.click(e -> {
            player.getInventory().addItem(foxSpawnEgg.getCleanItemStack());
        }));

        VexelCoreBukkitAPI.getMenuManager().open(menu,player);
    }
}
