package me.itsmcb.drusk.features.item;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemModelData extends CustomCommand {
    private final Drusk instance;
    public ItemModelData(Drusk instance) {
        super("modeldata", "Set item model data", "drusk.item.edit");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (!(cmdHelper.isInt(0))) {
            new BukkitMsgBuilder("&cMust be an integer!").send(player);
            return;
        }
        int mdata = Integer.parseInt(args[0]);
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            new BukkitMsgBuilder("&7Please put an item in your hand").send(player);
            return;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(mdata);
        item.setItemMeta(meta);
        new BukkitMsgBuilder("&7Set custom model data of &d"+item.getType().name()+" &7to &d"+mdata).send(player);
    }
}
