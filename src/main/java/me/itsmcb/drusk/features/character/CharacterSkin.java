package me.itsmcb.drusk.features.character;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("CharacterSkin")
public class CharacterSkin implements ConfigurationSerializable {

    private String name;
    private String value;
    private String signature;
    private String creator;

    public CharacterSkin(String name, String value, String signature, String creator) {
        this.name = name;
        this.value = value;
        this.signature = signature;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("name",name);
        map.put("value",value);
        map.put("signature",signature);
        map.put("creator",creator.toString());
        return map;
    }

    public static CharacterSkin deserialize(Map<String, Object> map) {
        return new CharacterSkin(
                (String) map.get("name"),
                (String) map.get("value"),
                (String) map.get("signature"),
                (String) map.get("creator")
        );
    }
}
