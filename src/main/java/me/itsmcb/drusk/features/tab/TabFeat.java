package me.itsmcb.drusk.features.tab;

import me.clip.placeholderapi.PlaceholderAPI;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;
import me.itsmcb.vexelcore.bukkit.VexelCoreRunnable;
import me.itsmcb.vexelcore.bukkit.VexelCoreRunnableInfo;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PluginUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TabFeat extends BukkitFeature {

    private Drusk instance;

    public TabFeat(Drusk instance) {
        super("tab","Tab list","tab", instance);
        this.instance = instance;
        // TODO register Runnable so that it can be disabled, reloaded, etc.
    }

    @Override
    public List<VexelCoreRunnableInfo> runnables() {
        String name = instance.getMainConfig().get().getString("tab.name");
        String top = format(instance.getMainConfig().get().getStringList("tab.top"));
        String bottom = format(instance.getMainConfig().get().getStringList("tab.bottom"));
        VexelCoreRunnable runnable = new VexelCoreRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Component header = new BukkitMsgBuilder(PluginUtils.pluginIsLoaded("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, top) : top).get();
                    Component footer = new BukkitMsgBuilder(PluginUtils.pluginIsLoaded("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, bottom) : bottom).get();
                    player.sendPlayerListHeaderAndFooter(header, footer);
                    Component newName = new BukkitMsgBuilder(PluginUtils.pluginIsLoaded("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, name) : name).get();
                    player.playerListName(newName);
                }
            }
        };
        return List.of(new VexelCoreRunnableInfo(runnable, VexelCoreRunnableInfo.RunnableType.REPEAT_ASYNC, 0, 20));
    }

    private String format(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            sb.append(strings.get(i));
            if (i+1 != strings.size()) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
