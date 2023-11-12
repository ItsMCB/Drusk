package me.itsmcb.drusk.features.tools;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.util.EulerAngle;

public class ArmorStandEditorList implements Listener {

    public static EulerAngle reset = new EulerAngle(0,0,0);

    private Drusk instance;

    public ArmorStandEditorList(Drusk instance) {
        this.instance = instance;
    }

    public static void resetAllPoses(ArmorStand armorStand) {
        armorStand.setHeadPose(reset);
        armorStand.setBodyPose(reset);
        armorStand.setLeftArmPose(reset);
        armorStand.setRightArmPose(reset);
        armorStand.setLeftLegPose(reset);
        armorStand.setRightLegPose(reset);
    }

    @EventHandler
    public void onBlockPlace(EntityDamageByEntityEvent event) {
        if (!(event.getDamager().getType().equals(EntityType.PLAYER) && event.getEntity().getType().equals(EntityType.ARMOR_STAND))) {
            return;
        }
        Player player = ((Player) event.getDamager()).getPlayer();
        if (player != null && !(player.isSneaking()) && !player.hasPermission("drusk.editor.armorstand")) {
            return;
        }
        ArmorStand armorStand = (ArmorStand) event.getEntity();
        ArmorStandEditStartEvent editStartEvent = new ArmorStandEditStartEvent(armorStand, player);
        Bukkit.getPluginManager().callEvent(editStartEvent);
        if (editStartEvent.isCancelled()) {
            return;
        }
        event.setCancelled(true);
        new BukkitMsgBuilder("&aYou selected an armor stand!").send(player);


        MenuV2 editor = new MenuV2("Armor Stand Editor", InventoryType.CHEST,27)
                .addItem(new MenuV2Item(Material.SMOOTH_STONE_SLAB).name("&aPlate").slot(1).leftClickAction(e -> {
                    armorStand.setBasePlate(!armorStand.hasBasePlate());
                }))
                .addItem(new MenuV2Item(Material.BONE).name("&aSize").slot(2).leftClickAction(e -> {
                    armorStand.setSmall(!armorStand.isSmall());
                }))
                .addItem(new MenuV2Item(Material.BONE).name("&aArms").slot(3).leftClickAction(e -> {
                    armorStand.setArms(!armorStand.hasArms());
                }))
                .addItem(new MenuV2Item(Material.SPIDER_EYE).name("&aVisible").slot(4).leftClickAction(e -> {
                    armorStand.setVisible(!armorStand.isVisible());
                }))
                .addItem(new MenuV2Item(Material.FEATHER).name("&aGravity").slot(5).leftClickAction(e -> {
                    armorStand.setGravity(!armorStand.hasGravity());
                }))
                .addItem(new MenuV2Item(Material.GLOW_INK_SAC).name("&aGlowing").slot(6).leftClickAction(e -> {
                    armorStand.setGlowing(!armorStand.isGlowing());
                }))
                .addItem(new MenuV2Item(Material.NAME_TAG).name("&aShow Name").slot(7).leftClickAction(e -> {
                    armorStand.setCustomNameVisible(!armorStand.isCustomNameVisible());
                }))
                .addItem(new MenuV2Item(Material.LEATHER_HELMET).name("&aHead Pose").slot(10).leftClickAction(e -> {
                    instance.getMenuManager().open(createPoseEditor(armorStand, PosePart.HEAD), player);
                }))
                .addItem(new MenuV2Item(Material.LEATHER_CHESTPLATE).name("&aBody Pose").slot(11).leftClickAction(e -> {
                    instance.getMenuManager().open(createPoseEditor(armorStand, PosePart.BODY), player);
                }))
                .addItem(new MenuV2Item(Material.STICK).name("&aLeft Arm Pose").slot(12).leftClickAction(e -> {
                    instance.getMenuManager().open(createPoseEditor(armorStand, PosePart.LEFT_ARM), player);
                }))
                .addItem(new MenuV2Item(Material.STICK).name("&aRight Arm Pose").slot(13).leftClickAction(e -> {
                    instance.getMenuManager().open(createPoseEditor(armorStand, PosePart.RIGHT_ARM), player);
                }))
                .addItem(new MenuV2Item(Material.LEATHER_LEGGINGS).name("&aLeft Leg Pose").slot(14).leftClickAction(e -> {
                    instance.getMenuManager().open(createPoseEditor(armorStand, PosePart.LEFT_LEG), player);
                }))
                .addItem(new MenuV2Item(Material.LEATHER_LEGGINGS).name("&aRight Leg Pose").slot(15).leftClickAction(e -> {
                    instance.getMenuManager().open(createPoseEditor(armorStand, PosePart.RIGHT_LEG), player);
                }))
                .addItem(new MenuV2Item(Material.CLOCK).name("&aReset Whole Body Pose").slot(16).leftClickAction(e -> {
                    resetAllPoses(armorStand);
                }))
                .addItem(new MenuV2Item(Material.RED_CONCRETE).name("&cRemove").addLore("&cIt can't be brought back once removed!").slot(22).leftClickAction(e -> {
                    armorStand.remove();
                    player.closeInventory();
                }));
        instance.getMenuManager().open(editor, player);
    }

    public enum PosePart {
        HEAD("Head"),
        BODY("Body"),
        LEFT_ARM("Left Arm"),
        RIGHT_ARM("Right Arm"),
        LEFT_LEG("Left Leg"),
        RIGHT_LEG("Right Leg");

        private String posePart;

        PosePart(String posePart) {
            this.posePart = posePart;
        }

        public String getPosePart() {
            return posePart;
        }

        @Override
        public String toString() {
            return String.valueOf(getPosePart());
        }
    }

    private MenuV2 createPoseEditor(ArmorStand armorStand, PosePart pose) {
        MenuV2 editor = new MenuV2(pose.getPosePart()+" Pose Editor",InventoryType.DISPENSER)
                .addItem(new MenuV2Item(Material.GREEN_WOOL).name("&e&lAdd X").slot(0).leftClickAction(e -> {
                    switch (pose) {
                        case HEAD -> armorStand.setHeadPose(armorStand.getHeadPose().add(0.25,0,0));
                        case BODY -> armorStand.setBodyPose(armorStand.getBodyPose().add(0.25,0,0));
                        case LEFT_ARM -> armorStand.setLeftArmPose(armorStand.getLeftArmPose().add(0.25,0,0));
                        case RIGHT_ARM -> armorStand.setRightArmPose(armorStand.getRightArmPose().add(0.25,0,0));
                        case LEFT_LEG -> armorStand.setLeftLegPose(armorStand.getLeftLegPose().add(0.25,0,0));
                        case RIGHT_LEG -> armorStand.setRightLegPose(armorStand.getRightLegPose().add(0.25,0,0));
                    }
                }))
                .addItem(new MenuV2Item(Material.RED_WOOL).name("&e&lRemove X").slot(3).leftClickAction(e -> {
                    switch (pose) {
                        case HEAD -> armorStand.setHeadPose(armorStand.getHeadPose().add(-0.25,0,0));
                        case BODY -> armorStand.setBodyPose(armorStand.getBodyPose().add(-0.25,0,0));
                        case LEFT_ARM -> armorStand.setLeftArmPose(armorStand.getLeftArmPose().add(-0.25,0,0));
                        case RIGHT_ARM -> armorStand.setRightArmPose(armorStand.getRightArmPose().add(-0.25,0,0));
                        case LEFT_LEG -> armorStand.setLeftLegPose(armorStand.getLeftLegPose().add(-0.25,0,0));
                        case RIGHT_LEG -> armorStand.setRightLegPose(armorStand.getRightLegPose().add(-0.25,0,0));
                    }
                }))
                .addItem(new MenuV2Item(Material.GREEN_WOOL).name("&e&lAdd Y").slot(1).leftClickAction(e -> {
                    switch (pose) {
                        case HEAD -> armorStand.setHeadPose(armorStand.getHeadPose().add(0,0.25,0));
                        case BODY -> armorStand.setBodyPose(armorStand.getBodyPose().add(0,0.25,0));
                        case LEFT_ARM -> armorStand.setLeftArmPose(armorStand.getLeftArmPose().add(0,0.25,0));
                        case RIGHT_ARM -> armorStand.setRightArmPose(armorStand.getRightArmPose().add(0,0.25,0));
                        case LEFT_LEG -> armorStand.setLeftLegPose(armorStand.getLeftLegPose().add(0,0.25,0));
                        case RIGHT_LEG -> armorStand.setRightLegPose(armorStand.getRightLegPose().add(0,0.25,0));
                    }
                }))
                .addItem(new MenuV2Item(Material.RED_WOOL).name("&e&lRemove Y").slot(4).leftClickAction(e -> {
                    switch (pose) {
                        case HEAD -> armorStand.setHeadPose(armorStand.getHeadPose().add(0,-0.25,0));
                        case BODY -> armorStand.setBodyPose(armorStand.getBodyPose().add(0,-0.25,0));
                        case LEFT_ARM -> armorStand.setLeftArmPose(armorStand.getLeftArmPose().add(0,-0.25,0));
                        case RIGHT_ARM -> armorStand.setRightArmPose(armorStand.getRightArmPose().add(0,-0.25,0));
                        case LEFT_LEG -> armorStand.setLeftLegPose(armorStand.getLeftLegPose().add(0,-0.25,0));
                        case RIGHT_LEG -> armorStand.setRightLegPose(armorStand.getRightLegPose().add(0,-0.25,0));
                    }
                }))
                .addItem(new MenuV2Item(Material.GREEN_WOOL).name("&e&lAdd Z").slot(2).leftClickAction(e -> {
                    switch (pose) {
                        case HEAD -> armorStand.setHeadPose(armorStand.getHeadPose().add(0,0,0.25));
                        case BODY -> armorStand.setBodyPose(armorStand.getBodyPose().add(0,0,0.25));
                        case LEFT_ARM -> armorStand.setLeftArmPose(armorStand.getLeftArmPose().add(0,0,0.25));
                        case RIGHT_ARM -> armorStand.setRightArmPose(armorStand.getRightArmPose().add(0,0,0.25));
                        case LEFT_LEG -> armorStand.setLeftLegPose(armorStand.getLeftLegPose().add(0,0,0.25));
                        case RIGHT_LEG -> armorStand.setRightLegPose(armorStand.getRightLegPose().add(0,0,0.25));
                    }
                }))
                .addItem(new MenuV2Item(Material.RED_WOOL).name("&e&lRemove Z").slot(5).leftClickAction(e -> {
                    switch (pose) {
                        case HEAD -> armorStand.setHeadPose(armorStand.getHeadPose().add(0,0,-0.25));
                        case BODY -> armorStand.setBodyPose(armorStand.getBodyPose().add(0,0,-0.25));
                        case LEFT_ARM -> armorStand.setLeftArmPose(armorStand.getLeftArmPose().add(0,0,-0.25));
                        case RIGHT_ARM -> armorStand.setRightArmPose(armorStand.getRightArmPose().add(0,0,-0.25));
                        case LEFT_LEG -> armorStand.setLeftLegPose(armorStand.getLeftLegPose().add(0,0,-0.25));
                        case RIGHT_LEG -> armorStand.setRightLegPose(armorStand.getRightLegPose().add(0,0,-0.25));
                    }
                }))
                .addItem(new MenuV2Item(Material.CLOCK).name("&e&lReset "+pose.posePart+" Pose").slot(7).leftClickAction(e -> {
                    switch (pose) {
                        case HEAD -> armorStand.setHeadPose(reset);
                        case BODY -> armorStand.setBodyPose(reset);
                        case LEFT_ARM -> armorStand.setLeftArmPose(reset);
                        case RIGHT_ARM -> armorStand.setRightArmPose(reset);
                        case LEFT_LEG -> armorStand.setLeftLegPose(reset);
                        case RIGHT_LEG -> armorStand.setRightLegPose(reset);
                    }
                }));
        return editor;
    }
}
