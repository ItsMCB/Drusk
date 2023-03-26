package me.itsmcb.drusk.features.skin;

import me.itsmcb.vexelcore.common.api.web.mojang.PlayerSkin;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("Costume")
public class DruskCostume extends PlayerSkin implements ConfigurationSerializable {

    private String name;
    private String value;
    private String signature;
    private String description;

    public DruskCostume(String name, String value, String signature) {
        super(value, signature);
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public DruskCostume(String name, String value, String signature, String description) {
        super(value, signature);
        this.name = name;
        this.value = value;
        this.signature = signature;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasDescription() {
        return description != null;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("name",name);
        map.put("value",value);
        map.put("signature",signature);
        map.put("description",description);
        return map;
    }

    public static DruskCostume deserialize(Map<String, Object> map) {
        return new DruskCostume(
                (String) map.get("name"),
                (String) map.get("value"),
                (String) map.get("signature"),
                (String) map.get("description")
        );
    }
}
