package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.utils.TimeUtils;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PlaytimeSCmd extends CustomCommand {

    private Drusk instance;

    public PlaytimeSCmd(Drusk instance) {
        super("playtime","View playtime leaderboard","drusk.admin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        List<OfflinePlayer> players = Arrays.stream(Bukkit.getOfflinePlayers()).sorted(Comparator.comparingInt(o -> o.getStatistic(Statistic.TOTAL_WORLD_TIME))).toList();

        for (int i = 1; i < players.size(); i++) {
            OfflinePlayer offlinePlayer = players.get(players.size()-i);
            new BukkitMsgBuilder("&d&l#"+(i)+" &b&l"+offlinePlayer.getName())
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, offlinePlayer.getUniqueId()+"")
                    .hover("&7Click to copy UUID")
                    .send(player);
            new BukkitMsgBuilder("&7- &dPlaytime: &e"+ TimeUtils.formatSecondsToTime(offlinePlayer.getStatistic(Statistic.TOTAL_WORLD_TIME)/20)).send(player);
            new BukkitMsgBuilder("&7- &dFirst Join: &e"+TimeUtils.concise2EpochDateFromMilliseconds(offlinePlayer.getFirstPlayed())).send(player);
            new BukkitMsgBuilder("&7- &dLast Seen: &e"+TimeUtils.concise2EpochDateFromMilliseconds(offlinePlayer.getLastSeen())).send(player);
        }
    }
}
