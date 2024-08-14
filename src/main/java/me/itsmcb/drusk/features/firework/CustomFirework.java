package me.itsmcb.drusk.features.firework;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("CustomFirework")
public class CustomFirework implements ConfigurationSerializable {

    private ItemStack item;
    private UUID creator;
    private boolean isPrivate = true;
    private String name;

    public CustomFirework(ItemStack item) {
        this.item = item;
    }

    public CustomFirework(ItemStack item, UUID creator, boolean isPrivate, String name) {
        this.item = item;
        this.creator = creator;
        this.isPrivate = isPrivate;
        this.name = name;
    }

    public ItemStack getItem() {
        return item;
    }

    public UUID getCreator() {
        return creator;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getName() {
        return name;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("item",item);
        map.put("creator",creator.toString());
        map.put("private",isPrivate);
        map.put("name",name);
        return map;
    }

    public static CustomFirework deserialize(Map<String, Object> map) {
        return new CustomFirework(
                (ItemStack) map.get("item"),
                UUID.fromString((String) map.get("creator")),
                (boolean) map.get("private"),
                (String) map.get("name")
        );
    }
}
