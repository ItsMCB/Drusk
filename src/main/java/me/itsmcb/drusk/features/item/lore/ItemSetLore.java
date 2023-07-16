package me.itsmcb.drusk.features.item.lore;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemSetLore extends CustomCommand {
    private final Drusk instance;
    public ItemSetLore(Drusk instance) {
        super("set", "Set item lore", "drusk.item.edit");
        this.instance = instance;
    }

    int line;
    ItemStack item;
    TextComponent component;

    public ItemSetLore(Drusk instance, ItemStack item, int line, TextComponent component) {
        this(instance);
        this.item = item;
        this.line = line;
        this.component = component;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        line--;
        ItemMeta meta = item.getItemMeta();
        ArrayList<Component> components = meta.hasLore() ? new ArrayList<>(meta.lore()) : new ArrayList<>();
        if (components.size() < line) {
            new BukkitMsgBuilder("&cPlease add lines up to that point. Current size is: "+components.size()).send(player);
            return;
        }
        if (components.size() == line) {
            components.add(component);
        } else {
            components.set(line,component);
        }
        meta.lore(components);
        item.setItemMeta(meta);
        new BukkitMsgBuilder("&7Set lore line of &d"+item.getType().name()+" &7to &d"+component.content()).send(player);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        if (sender instanceof Player player) {
            ItemStack hand = player.getInventory().getItemInMainHand();
            ItemMeta meta = hand.getItemMeta();
            if (meta != null) {
                ArrayList<String> temp = new ArrayList<>();
                if (meta.lore() != null) {
                    for (int i = 0; i < meta.lore().size()+1; i++) {
                        temp.add(""+(i+1));
                    }
                    return temp;
                } else {
                    return List.of("1");
                }
            }
        }
        return List.of("");
    }
}
