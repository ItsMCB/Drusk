package me.itsmcb.drusk.features.teleport;

import org.bukkit.entity.Player;

public class TeleportRequest {

    Player from;
    Player to;
    String reason = "No reason provided";

    public TeleportRequest(Player from, Player to, String reason) {
        this.from = from;
        this.to = to;
        if (reason != null) {
            this.reason = reason;
        }
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }
}
