package me.itsmcb.drusk.features.notify;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.itsmcb.vexelcore.bukkit.api.experience.SFX;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NotifySounds implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Bukkit.getOnlinePlayers().stream().filter(op -> !op.equals(e.getPlayer()) && op.hasPermission("drusk.msg.notify")).forEach(op -> {
            op.playSound(SFX.POP.getSound());
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getOnlinePlayers().stream().filter(op -> !op.equals(e.getPlayer()) && op.hasPermission("drusk.msg.notify")).forEach(op -> {
            op.playSound(SFX.PICKUP.getSound());
        });
    }

    private void notifyStaff(Player player) {

    }
}
