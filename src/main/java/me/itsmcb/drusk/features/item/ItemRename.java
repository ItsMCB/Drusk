package me.itsmcb.drusk.features.item;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRename extends CustomCommand {
    private final Drusk instance;
    public ItemRename(Drusk instance) {
        super("rename", "Set item name", "drusk.item.edit");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            new BukkitMsgBuilder("&7Please put an item in your hand").send(player);
            return;
        }
        if (args.length == 0) {
            new BukkitMsgBuilder("&cMust provide a new name.").send(player);
            return;
        }
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text().decoration(TextDecoration.ITALIC, false).append(new BukkitMsgBuilder(args[0]).get()).build());
        item.setItemMeta(meta);
        new BukkitMsgBuilder("&7Set name of &d"+item.getType().name()+" &7to &d"+args[0]).send(player);
    }
}
