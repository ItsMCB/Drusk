package me.itsmcb.drusk.pausedfeatures.flag;

import me.itsmcb.vexelcore.bukkit.api.utils.LocationUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlaceFlagListener implements Listener {

    @EventHandler
    public void placeFlagListener(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block l = player.getTargetBlock(null, 10).getRelative(event.getBlockFace());

        if (player.getInventory().getItemInMainHand().getType().equals(Material.BLACK_BANNER) && event.getAction().isRightClick()) {
            event.setCancelled(true);
            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(l.getLocation(), EntityType.ARMOR_STAND);
            LocationUtils.entityLookAtPlayer(player, armorStand);
            armorStand.setHeadPose(armorStand.getHeadPose().setZ(80.1));
            //EulerAngle angle = new EulerAngle(80.1,0,80.1);
            //armorStand.setHeadPose(angle);
            //armorStand.setInvisible(true);
            armorStand.setItem(EquipmentSlot.HEAD, player.getInventory().getItemInMainHand());
        }
    }
}
