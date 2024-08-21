package me.itsmcb.drusk.features.nickname;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("Nickname")
public class Nickname implements ConfigurationSerializable {

    private UUID player;
    private String name;

    public Nickname(UUID player, String name) {
        this.player = player;
        this.name = name;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("player",player.toString());
        map.put("name",name);
        return map;
    }

    public static Nickname deserialize(Map<String, Object> map) {
        return new Nickname(
                UUID.fromString((String) map.get("player")),
                (String) map.get("name")
        );
    }
}
