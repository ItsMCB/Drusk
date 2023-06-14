package me.itsmcb.drusk.features.tools;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ArmorStandEditStartEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private ArmorStand armorStand;
    private Player editor;

    public ArmorStandEditStartEvent(ArmorStand armorStand, Player editor) {
        this.armorStand = armorStand;
        this.editor = editor;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public Player getEditor() {
        return editor;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
