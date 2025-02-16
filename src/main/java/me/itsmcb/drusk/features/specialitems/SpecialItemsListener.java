package me.itsmcb.drusk.features.specialitems;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2ItemData;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Campfire;
import org.bukkit.block.Furnace;
import org.bukkit.block.Smoker;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.block.data.type.Light;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class SpecialItemsListener implements Listener {

    private Drusk instance;
    private NamespacedKey key;

    public SpecialItemsListener(Drusk instance, NamespacedKey key) {
        this.instance = instance;
        this.key = key;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        PersistentDataContainer container = event.getItemInHand().getItemMeta().getPersistentDataContainer();
        if (container.has(key)) {
            String data = container.get(key, PersistentDataType.STRING);
            if (data == null) {
                return;
            }
            if (data.equals("end_portal")) {
                setBlock(event.getBlockPlaced().getLocation(), Material.END_PORTAL);
            }
            if (data.equals("nether_portal_x")) {
                setBlock(event.getBlockPlaced().getLocation(), Material.NETHER_PORTAL,Axis.X);
            }
            if (data.equals("nether_portal_z")) {
                setBlock(event.getBlockPlaced().getLocation(), Material.NETHER_PORTAL,Axis.Z);
            }
            if (data.equals("lit_furnace")) {
                Furnace furnace = (Furnace) event.getBlockPlaced().getState();
                BlockData blockData = event.getBlock().getBlockData();
                ((Lightable) blockData).setLit(true);
                furnace.setBlockData(blockData);
                furnace.update(true,false);
            }
            if (data.equals("lit_smoker")) {
                Smoker smoker = (Smoker) event.getBlockPlaced().getState();
                BlockData blockData = event.getBlock().getBlockData();
                ((Lightable) blockData).setLit(true);
                smoker.setBlockData(blockData);
                smoker.update(true,false);
            }
            if (data.equals("lit_campfire")) {
                Campfire campfire = (Campfire) event.getBlockPlaced().getState();
                BlockData blockData = event.getBlock().getBlockData();
                ((Lightable) blockData).setLit(true);
                campfire.setBlockData(blockData);
                campfire.update(true,false);
            }
            if (data.equals("snowy_grass")) {
                Block block = event.getBlock();
                BlockData blockData = event.getBlock().getBlockData();
                ((Snowable) blockData).setSnowy(true);
                block.setBlockData(blockData);
            }
            if (data.equals("end_gateway")) {
                Block block = event.getBlock();
                block.setType(Material.END_GATEWAY);
            }
            if (data.equals("open_trapdoor")) {
                Block block = event.getBlock();
                Openable openable = (Openable) block.getBlockData();
                openable.setOpen(true);
                block.setBlockData(openable);
            }
        }
    }

    @EventHandler
    public void itemClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();
        if (itemStack == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        int heldSlot = event.getPlayer().getInventory().getHeldItemSlot();
        // Light Block - Editor
        if (action.equals(Action.RIGHT_CLICK_AIR) && itemStack.getType().equals(Material.LIGHT)) {
            MenuV2 menu = new MenuV2("&3Set Light Amount", InventoryType.CHEST,18).clickCloseMenu(true);
            for (int i = 1; i < 16; i++) {
                int finalI = i;
                MenuV2Item givenItem = new MenuV2Item(Material.LIGHT).name("&d&lLevel "+i);
                ItemMeta menuItemStackMeta = givenItem.getItemMeta();
                BlockDataMeta blockDataMeta = (BlockDataMeta) menuItemStackMeta;
                BlockData blockData = givenItem.getType().createBlockData();
                ((Light) blockData).setLevel(i);
                blockDataMeta.setBlockData(blockData);
                givenItem.setItemMeta(blockDataMeta);

                MenuV2Item item = givenItem.addLore("&7Edit the light level of your current light block.").leftClickAction(e -> {
                    if (e.getCurrentItem() == null) {
                        return;
                    }
                    event.getPlayer().getInventory().setItem(heldSlot, givenItem);
                    new BukkitMsgBuilder("&7Set light level to &3"+finalI).send(event.getPlayer());
                });
                menu.addItem(item);
            }
            instance.getMenuManager().open(menu, event.getPlayer());
        }
        // Requires data
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(key)) {
            String data = container.get(key, PersistentDataType.STRING);
            if (data == null) {
                return;
            }
            // Customizable Trapdoor Editor
            if (action.equals(Action.RIGHT_CLICK_AIR) && getTrapdoors().contains(event.getItem().getType()) && data.equals("open_trapdoor")) {
                MenuV2 menu = new MenuV2("&3Customize Trapdoor", InventoryType.CHEST,27).clickCloseMenu(true);
                getTrapdoors().forEach(td -> {
                    MenuV2Item item = new MenuV2Item(td).addLore("&7Customizble trapdoor (Right-Click)").leftClickAction(e -> {
                        if (e.getCurrentItem() == null) {
                            return;
                        }
                        event.getPlayer().getInventory().setItem(heldSlot, itemStack.withType(td));
                        new BukkitMsgBuilder("&7Set trapdoor type to &3"+td.name()).send(event.getPlayer());
                    });
                    menu.addItem(item);
                });
                instance.getMenuManager().open(menu, event.getPlayer());
            }

        }
        /*
        String data = itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (data == null) {
            return;
        }

        // Arctic Fox - Spawn
        if (action.equals(Action.RIGHT_CLICK_BLOCK) && itemStack.getType().equals(Material.FOX_SPAWN_EGG)) {
            if (data.equals("fox_snow")) {
                event.setCancelled(true); // Prevent a regular fox from being spawned
                Location location = event.getInteractionPoint();
                location.getWorld().spawn(location, Fox.class, CreatureSpawnEvent.SpawnReason.CUSTOM, fox -> {
                    fox.setFoxType(Fox.Type.SNOW);
                    fox.customName(null);  // Remove custom name (otherwise it will be the same as the spawn egg)
                });
            }
        }
         */
    }

    public List<Material> getTrapdoors() {
        return Arrays.stream(Material.values()).filter(m -> m.getKey().value().contains("trapdoor")).toList();
    }

    @EventHandler
    public void onEntityPlace(HangingPlaceEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(key)) {
            String data = container.get(key, PersistentDataType.STRING);
            if (data == null) {
                return;
            }
            if (data.equals("invisible_item_frame") && event.getEntity().getType().equals(EntityType.ITEM_FRAME)) {
                ItemFrame itemFrame = (ItemFrame) event.getEntity();
                itemFrame.setVisible(false);
            }
            if (data.equals("invisible_glow_item_frame") && event.getEntity().getType().equals(EntityType.GLOW_ITEM_FRAME)) {
                ItemFrame itemFrame = (ItemFrame) event.getEntity();
                itemFrame.setVisible(false);
            }
        }
    }

    private void setBlock(Location location, Material material, Axis axis) {
        Orientable orientable = (Orientable) material.createBlockData();
        orientable.setAxis(axis);
        location.getWorld().setBlockData(location, orientable);
    }

    private void setBlock(Location location, Material material) {
        location.getWorld().setBlockData(location, material.createBlockData());
    }
}
