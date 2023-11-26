package me.itsmcb.drusk.features.skin.copy;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HandScmd extends CustomCommand {

    private Drusk instance;

    public HandScmd(Drusk instance) {
        super("hand", "Copy skin of head in your main hand", "drusk.skin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        if (mainHandItem == null || (mainHandItem.getType() != Material.PLAYER_HEAD)) {
            new BukkitMsgBuilder("&cThere isn't a player head in your hand!").send(player);
            return;
        }
        SkullMeta skullMeta = (SkullMeta) mainHandItem.getItemMeta();
        if (skullMeta.getPlayerProfile() == null) {
            new BukkitMsgBuilder("&cThere isn't a player profile attached to this head!").send(player);
            return;
        }
        skullMeta.getPlayerProfile().getProperties().forEach(profileProperty -> {
            if (profileProperty.getSignature() == null) {
                new BukkitMsgBuilder("&cThis head has a texture but not a signature. The latter is required in order for this head to be applied as a skin.").send(player);
                return;
            }
            PlayerUtils.setSkin(player, profileProperty.getValue(), profileProperty.getSignature());
            new BukkitMsgBuilder("&aCopied skin of head!").send(player);
        });
    }
}
