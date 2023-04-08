package me.itsmcb.drusk.features.tools;

import me.itsmcb.drusk.Drusk;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MobSpawnerEditor implements Listener {

    private Drusk instance;

    public MobSpawnerEditor(Drusk instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBlockPlace(BlockBreakEvent event) {
        if (!(event.getBlock().getState() instanceof CreatureSpawner)) {
            return;
        }
        event.setCancelled(true);
        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
        spawner.setSpawnedType(EntityType.PIG);
        spawner.update(true,false);
    }
}
