package me.itsmcb.drusk.features.specialevents;

import me.clip.placeholderapi.PlaceholderAPI;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PluginUtils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class SpecialEventsListener implements Listener {

    private Drusk instance;

    public SpecialEventsListener(Drusk instance) {
        this.instance = instance;
    }

    @EventHandler
    public void Join(PlayerJoinEvent event) {
        Player ep = event.getPlayer();
        if (!instance.getMainConfig().get().getBoolean("events.join.enabled")) {
            return;
        }
        BukkitMsgBuilder msg;
        String text = instance.getMainConfig().get().getString("events.join.message");
        if (!ep.hasPlayedBefore()) {
            text = instance.getMainConfig().get().getString("events.join.first-join-message");
            msg = new BukkitMsgBuilder(
                    PluginUtils.pluginIsLoaded("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(ep, text) : text
            ).hover("&7Click to send first join welcome message").clickEvent(ClickEvent.Action.RUN_COMMAND,"/say Welcome, "+ep.getName());
            teleportToFirstJoin(ep);
        } else {
            msg = new BukkitMsgBuilder(
                    PluginUtils.pluginIsLoaded("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(ep, text) : text
            ).hover("&7Click to send welcome back message").clickEvent(ClickEvent.Action.RUN_COMMAND,"/say wb "+ep.getName());
            teleportToSpawn(ep);
        }
        event.joinMessage(null);
        msg.sendAllExcept(ep);
        msg.hover("&7Click to say \"hi\"").clickEvent(ClickEvent.Action.RUN_COMMAND,"/say hi").sendOnly(ep);

        // Title and sound
        if (instance.getMainConfig().get().getBoolean("events.join.enabled")) {
            // Title
            // TODO when title is blank don't send a message
            TextComponent titleMessage = new BukkitMsgBuilder(instance.getMainConfig().get().getString("events.join.title.title-message")).get();
            TextComponent subtitleMessage = new BukkitMsgBuilder(instance.getMainConfig().get().getString("events.join.title.subtitle-message")).get();
            Duration fadeIn = Duration.ofSeconds(instance.getMainConfig().get().getInt("events.join.title.fade-in"));
            Duration stay = Duration.ofSeconds(instance.getMainConfig().get().getInt("events.join.title.stay"));
            Duration fadeOut = Duration.ofSeconds(instance.getMainConfig().get().getInt("events.join.title.fade-out"));
            Title title = Title.title(titleMessage, subtitleMessage, Title.Times.times(fadeIn, stay, fadeOut));
            ep.showTitle(title);


            // Play sound
            // TODO make configurable
            Sound sound = Sound.sound(Key.key("block.note_block.bit"), Sound.Source.MASTER, 1f, 1f);
            ep.playSound(sound);
            new BukkitRunnable() {
                @Override
                public void run() {
                    Sound sound = Sound.sound(Key.key("block.note_block.bit"), Sound.Source.MASTER, 1f, 0.1f);
                    ep.playSound(sound);
                }
            }.runTaskLater(instance, 20L);
        }

    }

    @EventHandler
    public void Leave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TextComponent message = getMessage("leave","message", player);
        if (message != null) {
            event.quitMessage(message);
        }
    }

    @EventHandler
    public void Death(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        if (!event.isCancelled() && instance.getMainConfig().get().getBoolean("events.death.enabled")) {
            // TODO blank string don't send
            String message = instance.getMainConfig().get().getString("events.death.message");
            event.deathMessage(new BukkitMsgBuilder(
                    PluginUtils.pluginIsLoaded("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, message) : message
            ).get());
        }
    }
    private TextComponent getMessage(String type, String msg, Player player) {
        // Check if component is enabled
        if (instance.getMainConfig().get().getBoolean("events."+type+".enabled")) {
            // Return message
            String message = instance.getMainConfig().get().getString("events."+type+"."+msg);
            return new BukkitMsgBuilder(
                    PluginUtils.pluginIsLoaded("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, message) : message
            ).get();
        }
        // Return null if there is no new message to get
        return null;
    }

    private void teleportToSpawn(Player player) {
        try {
            Location spawn = (Location) instance.getMainConfig().get().get("spawn.location", Location.class);
            player.teleport(spawn);
        } catch (Exception e) {
            System.out.println("WARNING: Spawn location not set! Can't teleport player to spawn because of this issue.");
        }
    }

    private void teleportToFirstJoin(Player player) {
        try {
            Location spawn = (Location) instance.getMainConfig().get().get("spawn.first-join-location", Location.class);
            player.teleport(spawn);
        } catch (Exception e) {
            System.out.println("WARNING: Spawn location for first join not set! Teleporting player to regular spawn...");
            teleportToSpawn(player);
        }
    }
}
