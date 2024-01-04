package me.itsmcb.drusk.features.notify;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NotifySounds implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        notifyStaff(e.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        notifyStaff(e.getPlayer());
    }

    private void notifyStaff(Player player) {
        Bukkit.getOnlinePlayers().stream().filter(op -> !op.equals(player) && op.hasPermission("drusk.msg.notify")).forEach(op -> {
            op.playSound(op.getLocation(), Sound.ENTITY_SILVERFISH_AMBIENT, 1, 0);
        });
    }
}
