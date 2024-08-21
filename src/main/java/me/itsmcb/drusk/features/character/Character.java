package me.itsmcb.drusk.features.character;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("Character")
public class Character implements ConfigurationSerializable {

    private String name;
    private CharacterSkin characterSkin;
    private CharacterType characterType;
    private UUID creator;

    public Character(String name, CharacterSkin characterSkin, CharacterType characterType, UUID creator) {
        this.name = name;
        this.characterSkin = characterSkin;
        this.characterType = characterType;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public CharacterSkin getCharacterSkin() {
        return characterSkin;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public UUID getCreator() {
        return creator;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("name",name);
        map.put("skin",characterSkin);
        map.put("type",characterType.toString());
        map.put("creator",creator.toString());
        return map;
    }

    public static Character deserialize(Map<String, Object> map) {
        CharacterType characterType = CharacterType.UNKNOWN;
        // Attempt to set type, otherwise keep it as unknown
        try {
            CharacterType.valueOf((String) map.get("type"));
        } catch (Exception ignored) {}
        return new Character(
                (String) map.get("name"),
                (CharacterSkin) map.get("skin"),
                characterType,
                UUID.fromString((String) map.get("creator"))
        );
    }
}
