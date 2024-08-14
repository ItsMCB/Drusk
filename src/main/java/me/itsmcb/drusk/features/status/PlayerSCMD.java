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
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
        if (!cmdHelper.argExists(0)) {
            new BukkitMsgBuilder("&cProvide a username!").send(player);
            return;
        }
        // Offline data
        OfflinePlayer selectedPlayer = Bukkit.getOfflinePlayer(args[0]);
        new BukkitMsgBuilder("&8&m     &7&m     &8&m     &7&m     &r&8[ &3Player Status"+ (selectedPlayer.isOp() ? "&7(&dOperator&7)" : "")+" &8]&7&m     &8&m     &7&m     &8&m     ").send(player);
        new BukkitMsgBuilder("&8╔ &l&3Offline Player Data").send(player);
        new BukkitMsgBuilder("&8╠═ &7Username: &3"+selectedPlayer.getName())
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,selectedPlayer.getName())
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7UUID: &3"+selectedPlayer.getUniqueId())
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,selectedPlayer.getUniqueId()+"")
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7Server Playtime: &3"+TimeUtils.formatSecondsToTime(selectedPlayer.getStatistic(Statistic.TOTAL_WORLD_TIME)/20))
                .hover("&7Click to copy")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,TimeUtils.formatSecondsToTime(selectedPlayer.getStatistic(Statistic.TOTAL_WORLD_TIME)/20))
                .send(player);
        if (cmdHelper.argEquals(1,"--full")) {
            // VexelCore cached player data
            new BukkitMsgBuilder("&8║").send(player);
            new BukkitMsgBuilder("&8╠ &l&3Cached Player Data (VexelCore)").send(player);
            CachedPlayer cachedPlayer = new CachedPlayer(selectedPlayer.getUniqueId());
            new BukkitMsgBuilder("&8╠═ &7Username: &3"+cachedPlayer.getName())
                    .hover("&7Click to copy")
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,cachedPlayer.getName())
                    .send(player);
            new BukkitMsgBuilder("&8╠═ &7UUID: &3"+cachedPlayer.getUUID())
                    .hover("&7Click to copy")
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,cachedPlayer.getUUID()+"")
                    .send(player);
            String skinValue = cachedPlayer.getPlayerSkin().getValue();
            String skinSignature = cachedPlayer.getPlayerSkin().getSignature();
            new BukkitMsgBuilder("&8╠═ &7Skin Value: &3Hidden (hover to view)")
                    .hover("&7Click to copy: &3"+skinValue)
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,skinValue)
                    .send(player);
            new BukkitMsgBuilder("&8╠═ &7Skin Signature: &3Hidden (hover to view)")
                    .hover("&7Click to copy: &3"+skinSignature)
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,skinSignature)
                    .send(player);
        }
        // Online data
        Player selectedOnlinePlayer = selectedPlayer.getPlayer();
        if (selectedOnlinePlayer == null) {
            return;
        }
        new BukkitMsgBuilder("&8║").send(player);
        new BukkitMsgBuilder("&8╠ &l&3Online Player Data").send(player);
        new BukkitMsgBuilder("&8╠═ &7World: &3"+selectedOnlinePlayer.getWorld().getName())
                .hover("&7Click to teleport")
                .clickEvent(ClickEvent.Action.RUN_COMMAND,"/world teleport "+selectedOnlinePlayer.getWorld().getName())
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7Ping: &3"+selectedOnlinePlayer.getPing())
                .hover("&7Click to copy ping")
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,selectedOnlinePlayer.getPing()+"")
                .send(player);
        new BukkitMsgBuilder("&8╠═ &7IP Address: &3Hidden (hover to view)")
                .hover("&7Click to copy: &3"+selectedOnlinePlayer.getAddress().getAddress())
                .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,selectedOnlinePlayer.getAddress().getAddress()+"")
                .send(player);
        if (cmdHelper.argEquals(1,"--full")) {
            new BukkitMsgBuilder("&8╠═ &7Client Brand: &3"+selectedOnlinePlayer.getClientBrandName())
                    .hover("&7Click to copy")
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,selectedOnlinePlayer.getClientBrandName())
                    .send(player);
            new BukkitMsgBuilder("&8╠═ &7Client View Distance: &3"+selectedOnlinePlayer.getViewDistance())
                    .hover("&7Click to copy")
                    .clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,selectedOnlinePlayer.getViewDistance()+"")
                    .send(player);
            new BukkitMsgBuilder("&8╠═ &7Client Locale: &3"+selectedOnlinePlayer.locale().getDisplayName() +" | "+selectedOnlinePlayer.locale().getCountry()).send(player);
            new BukkitMsgBuilder("&8╠═ &7Client Chat Visibility: &3"+selectedOnlinePlayer.getClientOption(ClientOption.CHAT_VISIBILITY).name()).send(player);
            // CoreProtect data
            new BukkitMsgBuilder("&8║").send(player);
            new BukkitMsgBuilder("&8╠ &l&3CoreProtect Data").send(player);
            CoreProtectAPI coreProtectAPI = PluginUtils.getCoreProtect();
            if (coreProtectAPI == null) { // Ensure we have access to the API
                new BukkitMsgBuilder("&cCoreProtect is not installed").send(player);
                return;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    List<String[]> blocksBrokenLookup = coreProtectAPI.performLookup(2630000, Collections.singletonList(selectedPlayer.getName()),null,null,null, Arrays.asList(0), 0, null);
                    List<String[]> blocksPlacedLookup = coreProtectAPI.performLookup(2630000, Collections.singletonList(selectedPlayer.getName()),null,null,null, Arrays.asList(1), 0, null);
                    List<String[]> interactionLookup = coreProtectAPI.performLookup(2630000, Collections.singletonList(selectedPlayer.getName()),null,null,null, Arrays.asList(2), 0, null);

                    new BukkitMsgBuilder("&8╠═ &7Blocks Broken:").hover("View more logs").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/co lookup "+selectedPlayer.getName()+" time:1w ").send(player);
                    sendCoreProtectLookupFormatted(blocksBrokenLookup, player);
                    new BukkitMsgBuilder("&8╠═ &7Blocks Placed:").hover("View more logs").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/co lookup "+selectedPlayer.getName()+" time:1w ").send(player);
                    sendCoreProtectLookupFormatted(blocksPlacedLookup, player);
                    new BukkitMsgBuilder("&8╠═ &7Interactions:").hover("View more logs").clickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/co lookup "+selectedPlayer.getName()+" time:1w ").send(player);
                    sendCoreProtectLookupFormatted(interactionLookup, player);
                }
            }.runTaskAsynchronously(instance);
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
            new BukkitMsgBuilder("&8╠══ &9"+parseResult.getType().name()+" &7- &e@ "+x+" "+y+" "+z)
                    .hover("&7Click to teleport")
                    .clickEvent(ClickEvent.Action.RUN_COMMAND,"/teleport "+x+" "+y+" "+z)
                    .send(player);
        }
    }
}
