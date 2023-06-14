package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PluginUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import me.itsmcb.vexelcore.common.api.utils.TimeUtils;
import net.coreprotect.CoreProtectAPI;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerSCMD extends CustomCommand {

    private Drusk instance;

    public PlayerSCMD(Drusk instance) {
        super("player","View status of a player","drusk.admin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        // Custom command to do: require arg
        if (cmdHelper.argExists(0)) {
            OfflinePlayer selectedPlayer = Bukkit.getOfflinePlayer(args[0]);
            if (!(selectedPlayer.hasPlayedBefore())) {
                return;
            }
            new BukkitMsgBuilder(
                    "\n&e&l"+
                            selectedPlayer.getName() + " &7- &e&l" +
                            TimeUtils.formatSecondsToTime(selectedPlayer.getStatistic(Statistic.TOTAL_WORLD_TIME)/20)
            ).send(player);
            if (selectedPlayer instanceof Player onlinePlayer) {
                new BukkitMsgBuilder(
                        "&7- &e" + onlinePlayer.getClientBrandName() + "&7, &e" + onlinePlayer.getPing()+"ms"
                ).send(player);
                new BukkitMsgBuilder("&7- &eIP").hover("&7"+onlinePlayer.getAddress().getAddress()).send(player);
            }
            new BukkitMsgBuilder("&7- &aFirst Played " + TimeUtils.concise2EpochDateFromMilliseconds(selectedPlayer.getFirstPlayed())).send(player);
            new BukkitMsgBuilder("&7- &cLast Seen " + TimeUtils.concise2EpochDateFromMilliseconds(selectedPlayer.getLastSeen())+"\n").send(player);

            // CoreProtect extension
            CoreProtectAPI coreProtectAPI = PluginUtils.getCoreProtect();
            if (coreProtectAPI == null){ // Ensure we have access to the API
                return;
            }

            // CoreProtect results
            List<String[]> blocksBrokenLookup = coreProtectAPI.performLookup(2630000, Collections.singletonList(selectedPlayer.getName()),null,null,null, Arrays.asList(0), 0, null);
            List<String[]> blocksPlacedLookup = coreProtectAPI.performLookup(2630000, Collections.singletonList(selectedPlayer.getName()),null,null,null, Arrays.asList(1), 0, null);
            List<String[]> interactionLookup = coreProtectAPI.performLookup(2630000, Collections.singletonList(selectedPlayer.getName()),null,null,null, Arrays.asList(2), 0, null);

            new BukkitMsgBuilder("&7- &eBlocks Broken:").hover("View more logs").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/co lookup "+selectedPlayer.getName()+" time:1w ").send(player);
            sendCoreProtectLookupFormatted(blocksBrokenLookup, player);
            new BukkitMsgBuilder("&7- &eBlocks Placed:").hover("View more logs").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/co lookup "+selectedPlayer.getName()+" time:1w ").send(player);
            sendCoreProtectLookupFormatted(blocksPlacedLookup, player);
            new BukkitMsgBuilder("&7- &eInteractions:").hover("View more logs").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/co lookup "+selectedPlayer.getName()+" time:1w ").send(player);
            sendCoreProtectLookupFormatted(interactionLookup, player);
            return;
        }
        // instance.getServer().getServicesManager().load(LuckPerms.class).getUserManager().getUser(selectedPlayer.getUniqueId()).getPrimaryGroup() + " &7- &e&l" +
        new BukkitMsgBuilder("Please provide a valid username!").send(player);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList();
    }

    private void sendCoreProtectLookupFormatted(List<String[]> list, Player player) {
        if (list == null) {
            return;
        }
        for (int i = 0; (i < (Math.min(list.size(), 3))); i++) {
            CoreProtectAPI.ParseResult parseResult = PluginUtils.getCoreProtect().parseResult(list.get(i));
            int x = parseResult.getX();
            int y = parseResult.getY();
            int z = parseResult.getZ();
            new BukkitMsgBuilder("  &7- &d"+parseResult.getBlockData().getMaterial().getKey()+" &7- &e@ "+x+" "+y+" "+z)
                    .hover("&7Click to teleport")
                    .clickEvent(ClickEvent.Action.RUN_COMMAND,"/teleport "+x+" "+y+" "+z)
                    .send(player);
        }
    }
}
