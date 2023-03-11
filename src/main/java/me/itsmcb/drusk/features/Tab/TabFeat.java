package me.itsmcb.drusk.features.Tab;

import me.clip.placeholderapi.PlaceholderAPI;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;
import me.itsmcb.vexelcore.bukkit.VexelCoreRunnable;
import me.itsmcb.vexelcore.bukkit.VexelCoreRunnableInfo;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TabFeat extends BukkitFeature {

    private Drusk instance;

    public TabFeat(Drusk instance) {
        super("tab","Tab feature","tab", instance);
        this.instance = instance;
        // TODO register Runnable so that it can be disabled, reloaded, etc.
    }

    @Override
    public List<VexelCoreRunnableInfo> runnables() {
        String top = format(instance.getMainConfig().get().getStringList("tab.top"));
        String bottom = format(instance.getMainConfig().get().getStringList("tab.bottom"));
        VexelCoreRunnable runnable = new VexelCoreRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Component header = new BukkitMsgBuilder(PlaceholderAPI.setPlaceholders(player, top)).get();
                    Component footer = new BukkitMsgBuilder(PlaceholderAPI.setPlaceholders(player, bottom)).get();
                    player.sendPlayerListHeaderAndFooter(header, footer);
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
