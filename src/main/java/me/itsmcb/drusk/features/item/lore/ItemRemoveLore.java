package me.itsmcb.drusk.features.item.lore;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemRemoveLore extends CustomCommand {
    private final Drusk instance;
    public ItemRemoveLore(Drusk instance) {
        super("remove", "Remove item lore by line", "drusk.item.edit");
        this.instance = instance;
    }

    int line;
    ItemStack item;

    public ItemRemoveLore(Drusk instance, ItemStack item, int line) {
        this(instance);
        this.item = item;
        this.line = line;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        line--; // User input 1, so line=0
        ItemMeta meta = item.getItemMeta();
        ArrayList<Component> components = meta.hasLore() ? new ArrayList<>(meta.lore()) : new ArrayList<>();
        if (components.size() <= line) {
            new BukkitMsgBuilder("&cLine doesn't exist. Current size is: "+components.size()).send(player);
            return;
        }
        components.remove(line);
        meta.lore(components);
        item.setItemMeta(meta);
        new BukkitMsgBuilder("&7Removed lore line of &d"+item.getType().name()).send(player);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        if (sender instanceof Player player) {
            ItemStack hand = player.getInventory().getItemInMainHand();
            ItemMeta meta = hand.getItemMeta();
            if (meta != null) {
                ArrayList<String> temp = new ArrayList<>();
                if (meta.lore() != null) {
                    for (int i = 0; i < meta.lore().size(); i++) {
                        temp.add(""+(i+1));
                    }
                    return temp;
                }
            }
        }
        return List.of("");
    }
}
