package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import me.itsmcb.vexelcore.common.api.utils.TimeUtils;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PlayersSCmd extends CustomCommand {

    private Drusk instance;

    public PlayersSCmd(Drusk instance) {
        super("players","View status players who've joined the server","drusk.admin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        List<OfflinePlayer> sortedOfflinePlayers = Arrays.stream(Bukkit.getOfflinePlayers()).sorted(Comparator.comparingLong(OfflinePlayer::getLastSeen).reversed()).toList();
        if (cmdHelper.isCalling("-l")) {
            sortedOfflinePlayers.forEach(offlinePlayer -> {
                new BukkitMsgBuilder("&b"+offlinePlayer.getName() + " &7| &cLast Seen:&e " + TimeUtils.conciseEpochDateFromMilliseconds(offlinePlayer.getLastSeen()))
                        .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, offlinePlayer.getUniqueId()+"")
                        .hover("&7Click to copy UUID")
                        .send(player);
            });
        } else {
            new BukkitMsgBuilder("&aPlayer Join List:").send(player);
            sortedOfflinePlayers.forEach(offlinePlayer -> {
                new BukkitMsgBuilder("&b"+offlinePlayer.getName() + "\n &7- &aFirst Played:&e " + TimeUtils.conciseEpochDateFromMilliseconds(offlinePlayer.getFirstPlayed()) + "\n &7- &cLast Seen:&e " + TimeUtils.conciseEpochDateFromMilliseconds(offlinePlayer.getLastSeen()))
                        .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, offlinePlayer.getUniqueId()+"")
                        .hover("&7Click to copy UUID")
                        .send(player);
            });
        }
    }

    @Override
    public List<String> getAdditionalCompletions() {
        return List.of("-l");
    }
}
