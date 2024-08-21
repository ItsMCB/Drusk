package me.itsmcb.drusk.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.drusk.features.nickname.NicknameCmd;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PAPI extends PlaceholderExpansion {

    private Drusk instance;
    public PAPI(Drusk instance) {
        this.instance = instance;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "drusk";
    }

    @Override
    public String getAuthor(){
        return instance.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("nickname_or_playername")) {
            String nick =  new NicknameCmd(instance).getNickname(player.getUniqueId());
            return nick == null ? player.getName() : nick;
        }
        return null;
    }
}
