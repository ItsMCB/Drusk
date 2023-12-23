package me.itsmcb.drusk.features.status;

import com.destroystokyo.paper.ClientOption;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PluginUtils;
import me.itsmcb.vexelcore.bukkit.plugin.CachedPlayer;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import me.itsmcb.vexelcore.common.api.utils.TimeUtils;
import net.coreprotect.CoreProtectAPI;
import net.kyori.adventure.text.TextComponent;
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
            if (!(selectedPlayer.hasPlayedBefore() || selectedPlayer.isOnline())) {
                new BukkitMsgBuilder("&cPlayer hasn't played before!").send(player);
                return;
            }

            TextComponent bar = new BukkitMsgBuilder("&7===== ").get();

            // Initial information
            TextComponent username = new BukkitMsgBuilder("&d"+selectedPlayer.getName()).hover("&7Click to copy").clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, selectedPlayer.getName()).get();
            TextComponent uuid = new BukkitMsgBuilder("&7 (&5"+selectedPlayer.getUniqueId()+"&7) ").hover("&7Click to copy").clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,selectedPlayer.getUniqueId()+"").get();
            player.sendMessage(bar.append(username).append(uuid.append(bar)));
            // History
            new BukkitMsgBuilder(
                    "\n&7Playtime: &3"+ TimeUtils.formatSecondsToTime(selectedPlayer.getStatistic(Statistic.TOTAL_WORLD_TIME)/20)
            ).send(player);
            // Online information
            if (selectedPlayer instanceof Player onlinePlayer) {
                new BukkitMsgBuilder("&3Client Information").hover(
                        "&7Brand Name: &3"+onlinePlayer.getClientBrandName()+"\n"+
                                "&7Ping: &3"+onlinePlayer.getPing()+"\n"+
                                "&7View Distance: &3"+onlinePlayer.getClientViewDistance()+"\n"+
                                "&7Locale: &3"+onlinePlayer.getClientOption(ClientOption.LOCALE)+"\n"+
                                "&7Chat Visibility: &3"+onlinePlayer.getClientOption(ClientOption.CHAT_VISIBILITY).name()+"\n"+
                                "&7Chat Colors Enabled: &3"+onlinePlayer.getClientOption(ClientOption.CHAT_COLORS_ENABLED)+"\n"+
                                "&7Allow Server Listings: &3"+onlinePlayer.getClientOption(ClientOption.ALLOW_SERVER_LISTINGS)+"\n"+
                                "&7Main Hand: &3"+onlinePlayer.getClientOption(ClientOption.MAIN_HAND)+"\n"+
                                "&7Text Filtering Enabled: &3"+onlinePlayer.getClientOption(ClientOption.TEXT_FILTERING_ENABLED)+"\n"+
                                "&7Has Cape: &3"+onlinePlayer.getClientOption(ClientOption.SKIN_PARTS).hasCapeEnabled()
                ).send(player);
            }
            new BukkitMsgBuilder("&8- &aFirst Played: " + TimeUtils.concise2EpochDateFromMilliseconds(selectedPlayer.getFirstPlayed())).send(player);
            new BukkitMsgBuilder("&8- &cLast Seen: " + TimeUtils.concise2EpochDateFromMilliseconds(selectedPlayer.getLastSeen())+"\n").send(player);

            CachedPlayer cachedPlayer = new CachedPlayer(selectedPlayer.getUniqueId());
            String value = cachedPlayer.getPlayerSkin().getValue();
            new BukkitMsgBuilder("&3Skin Value").hover("&7"+value).clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,value).send(player);
            String signature = cachedPlayer.getPlayerSkin().getSignature();
            new BukkitMsgBuilder("&3Skin Signature").hover("&7"+signature).clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,signature).send(player);

            // CoreProtect extension
            CoreProtectAPI coreProtectAPI = PluginUtils.getCoreProtect();
            if (coreProtectAPI == null){ // Ensure we have access to the API
                return;
            }

            // CoreProtect results
            List<String[]> blocksBrokenLookup = coreProtectAPI.performLookup(2630000, Collections.singletonList(selectedPlayer.getName()),null,null,null, Arrays.asList(0), 0, null);
            List<String[]> blocksPlacedLookup = coreProtectAPI.performLookup(2630000, Collections.singletonList(selectedPlayer.getName()),null,null,null, Arrays.asList(1), 0, null);
            List<String[]> interactionLookup = coreProtectAPI.performLookup(2630000, Collections.singletonList(selectedPlayer.getName()),null,null,null, Arrays.asList(2), 0, null);

            new BukkitMsgBuilder("&8- &eBlocks Broken:").hover("View more logs").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/co lookup "+selectedPlayer.getName()+" time:1w ").send(player);
            sendCoreProtectLookupFormatted(blocksBrokenLookup, player);
            new BukkitMsgBuilder("&8- &eBlocks Placed:").hover("View more logs").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/co lookup "+selectedPlayer.getName()+" time:1w ").send(player);
            sendCoreProtectLookupFormatted(blocksPlacedLookup, player);
            new BukkitMsgBuilder("&8- &eInteractions:").hover("View more logs").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/co lookup "+selectedPlayer.getName()+" time:1w ").send(player);
            sendCoreProtectLookupFormatted(interactionLookup, player);
        }
        // instance.getServer().getServicesManager().load(LuckPerms.class).getUserManager().getUser(selectedPlayer.getUniqueId()).getPrimaryGroup() + " &7- &e&l" +
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
            new BukkitMsgBuilder("  &7- &3"+parseResult.getType().name()+" &7- &e@ "+x+" "+y+" "+z)
                    .hover("&7Click to teleport")
                    .clickEvent(ClickEvent.Action.RUN_COMMAND,"/teleport "+x+" "+y+" "+z)
                    .send(player);
        }
    }
}
