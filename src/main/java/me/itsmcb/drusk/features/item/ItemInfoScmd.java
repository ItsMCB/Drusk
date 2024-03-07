package me.itsmcb.drusk.features.item;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemInfoScmd extends CustomCommand {
    private final Drusk instance;
    public ItemInfoScmd(Drusk instance) {
        super("info", "", "drusk.item.edit");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            new BukkitMsgBuilder("&7Please put an item in your hand").send(player);
            return;
        }
        new BukkitMsgBuilder("&8--=== &d&lItem Information &8===--").send(player);
        new BukkitMsgBuilder("&7Type: &d"+item.getType().getKey()).send(player);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        if (itemMeta.hasDisplayName()) {
            player.sendMessage(new BukkitMsgBuilder("&7Display Name: &d").get().append(itemMeta.displayName()));
        }
        if (itemMeta.hasLore()) {
            new BukkitMsgBuilder("&7Lore: ").send(player);
            itemMeta.lore().forEach(component -> {
                player.sendMessage(new BukkitMsgBuilder("  ").get().append(component));
            });
        }
        if (itemMeta.hasCustomModelData()) {
            new BukkitMsgBuilder("&7Custom Model Data: &d"+itemMeta.getCustomModelData()).send(player);
        }
        if (!itemMeta.getPersistentDataContainer().getKeys().isEmpty()) {
            new BukkitMsgBuilder("&7Persistent Data Container Data: ").send(player);
            itemMeta.getPersistentDataContainer().getKeys().forEach(key -> {
                new BukkitMsgBuilder("&8- &7Key: &d"+key.getKey()+" &8 | &7Value: &d"+key.value()).send(player);
            });
        }
        if (!itemMeta.getItemFlags().isEmpty()) {
            new BukkitMsgBuilder("&7Item Flags:").send(player);
            itemMeta.getItemFlags().forEach(flag -> {
                new BukkitMsgBuilder("&8- &d"+flag.name()).send(player);
            });
        }
        if ((itemMeta.getAttributeModifiers() != null) && (!itemMeta.getAttributeModifiers().isEmpty())) {
            new BukkitMsgBuilder("&7Attribute Modifier:").send(player);
            itemMeta.getAttributeModifiers().asMap().forEach((a,c) -> {
                new BukkitMsgBuilder("&7Namespace: &d"+a.getKey().getNamespace()).send(player);
                new BukkitMsgBuilder("&7Key: &d"+a.getKey().getKey()).send(player);
                c.forEach(d -> {
                    new BukkitMsgBuilder("&7Name: &d"+d.getName()).send(player);
                    new BukkitMsgBuilder("&7Amount: &d"+d.getAmount()).send(player);
                    new BukkitMsgBuilder("&7Operation: &d"+d.getOperation().name()).send(player);
                });
            });
        }
        if (!itemMeta.getDestroyableKeys().isEmpty()) {
            new BukkitMsgBuilder("&7Destroyable Keys:").send(player);
            itemMeta.getDestroyableKeys().forEach(k -> {
                new BukkitMsgBuilder("&7"+k.getNamespace()+": &d"+k.getKey()).send(player);
            });
        }





    }
}
