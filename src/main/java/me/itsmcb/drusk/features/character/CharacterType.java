package me.itsmcb.drusk.features.character;

public enum CharacterType {

    COMMUNITY("Community"),
    MOJANG("Mojang"),
    UNKNOWN("Unknown");

    private String type;

    CharacterType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
