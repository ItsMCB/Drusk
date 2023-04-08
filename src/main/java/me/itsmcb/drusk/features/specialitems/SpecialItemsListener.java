package me.itsmcb.drusk.features.specialitems;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SpecialItemsListener implements Listener {

    private NamespacedKey key;

    public SpecialItemsListener(NamespacedKey key) {
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
        }
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
