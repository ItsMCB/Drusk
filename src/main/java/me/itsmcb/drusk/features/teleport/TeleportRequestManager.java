package me.itsmcb.drusk.features.teleport;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;

public class TeleportRequestManager {

    Drusk instance;

    ArrayList<TeleportRequest> teleportRequests = new ArrayList<>();

    public void add(@NotNull TeleportRequest request) {
        this.teleportRequests.add(request);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (teleportRequests.contains(request)) {
                    teleportRequests.remove(request);
                    new BukkitMsgBuilder("&7Teleport request to &d"+request.getTo().getName()+" expired").send(request.getFrom());
                }
            }
        }.runTaskLater(instance,20*60*5); // Request expires in 5 mins
    }

    public void remove(@NotNull Player from,Player to) {
        Optional<TeleportRequest> optional = teleportRequests.stream().filter(req -> (req.from == from) && (req.to == to)).findFirst();
        if (optional.isEmpty()) {
            System.out.println("Could not find teleport request?");
            return;
        }
        teleportRequests.remove(optional.get());
    }

    public void accept(Player to, Player from) {
        Optional<TeleportRequest> optional = teleportRequests.stream().filter(req -> (req.from == from) && (req.to == to)).findFirst();
        if (optional.isPresent()) {
            teleportRequests.remove(optional.get());
            TeleportRequest request = optional.get();
            request.getFrom().teleport(to.getLocation());
            BukkitMsgBuilder msg = new BukkitMsgBuilder("&a"+request.getTo().getName()+"&7 accepted teleport request from &d"+request.getFrom().getName());
            msg.send(request.getFrom());
            msg.send(request.getTo());
        } else {
            new BukkitMsgBuilder("&cThere aren't any requests to accept from "+from.getName()).send(to);
        }
    }

    public ArrayList<TeleportRequest> getTeleportRequests() {
        return teleportRequests;
    }

    public TeleportRequestManager(Drusk instance) {
        this.instance = instance;
    }
}
