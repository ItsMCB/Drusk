package me.itsmcb.drusk.features.gamemode;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeFeat extends BukkitFeature {

    public GamemodeFeat(Drusk instance) {
        super("gamemode", "Manage mode of self and others", null, instance);
        registerCommand(new GamemodeCmd(instance));
        registerCommand(new CreativeCmd(instance,"gmc"));
        registerCommand(new SurvivalCmd(instance, "gms"));
        registerCommand(new AdventureCmd(instance, "gma"));
        registerCommand(new SpectatorCmd(instance, "gmsp"));
    }

    public static boolean setGameMode(Player playerTrying, GameMode gameMode, Player playerAffected) {
        playerAffected.setGameMode(gameMode);
        new BukkitMsgBuilder("&7Set gamemode of &a" + playerAffected.getName() + "&7 to &a" + gameMode.name() + "&7.").send(playerTrying);
        return true;
    }
}
