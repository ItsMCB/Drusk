package me.itsmcb.drusk.features.item.lore;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemLore extends CustomCommand {
    private final Drusk instance;
    public ItemLore(Drusk instance) {
        super("lore", "Edit item lore", "drusk.item.edit");
        this.instance = instance;
        registerStipulatedSubCommand(new ItemSetLore(instance));
        registerStipulatedSubCommand(new ItemRemoveLore(instance));
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            new BukkitMsgBuilder("&7Please put an item in your hand").send(player);
            return;
        }
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.argEquals(0,"reset")) {
            ItemMeta meta = item.getItemMeta();
            meta.lore(null);
            item.setItemMeta(meta);
            new BukkitMsgBuilder("&7Successfully reset item's lore.").send(player);
            return;
        }
        int lineNumber = 1;
        try {
            lineNumber = Integer.parseInt(args[1]);
            if (lineNumber < 1) {
                throw new RuntimeException("Invalid line number");
            }
        } catch (RuntimeException e) {
            new BukkitMsgBuilder("&cMust provide a positive integer (1+)!").send(player);
            return;
        }
        // Just needs lore line
        if (cmdHelper.argEquals(0, "remove")) {
            new ItemRemoveLore(instance,item,lineNumber).execute(player, "remove",args);
            return;
        }
        // Needs item lore line to add
        if (cmdHelper.argNotExists(2)) {
            new BukkitMsgBuilder("&cMust provide line number and new lore line.").send(player);
            return;
        }
        TextComponent tc = Component.text().decoration(TextDecoration.ITALIC, false).append(new BukkitMsgBuilder(args[2]).get()).build();
        if (cmdHelper.argEquals(0, "set")) {
            new ItemSetLore(instance,item,lineNumber,tc).execute(player, "set",args);
        }
    }
}
