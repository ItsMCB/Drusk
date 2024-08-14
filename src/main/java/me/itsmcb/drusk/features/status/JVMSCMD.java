package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.utils.TimeUtils;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

public class JVMSCMD extends CustomCommand {

    private Drusk instance;

    public JVMSCMD(Drusk instance) {
        super("jvm","View status of the JVM","drusk.admin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        new BukkitMsgBuilder("&8&m     &7&m     &8&m     &7&m     &r&8[ &3JVM Status &8]&7&m     &8&m     &7&m     &8&m     ").send(player);
        // Memory
        double memoryUsed = ((Runtime.getRuntime().totalMemory()/1048576f)-(Runtime.getRuntime().freeMemory()/1048576f));
        double memoryTotal = (Runtime.getRuntime().totalMemory()/1048576f);
        double memoryUsedPercent = ((memoryUsed/memoryTotal)*100);
        DecimalFormat percentDecimalFormat = new DecimalFormat("#.##");
        new BukkitMsgBuilder("&8╠═ &7Uptime: &3"+TimeUtils.formatSecondsToTime((int) (ManagementFactory.getRuntimeMXBean().getUptime()/1000))).send(player);
        new BukkitMsgBuilder("&8╠═ &7Memory Usage: &3"+ percentDecimalFormat.format(memoryUsedPercent) + "% &7(&3" + percentDecimalFormat.format(memoryUsed) + " mb&7/&3"+percentDecimalFormat.format(memoryTotal)+" mb&7)").send(player);
        new BukkitMsgBuilder("&8╠═ &7Platform: &3"+System.getProperty("os.arch") + " &7(&3"+System.getProperty("os.name")+"&7)").send(player);

        // JVM
        String JVMName = ManagementFactory.getRuntimeMXBean().getVmName();
        String JVMVendor = ManagementFactory.getRuntimeMXBean().getVmVendor();
        String JVMVersion = ManagementFactory.getRuntimeMXBean().getVmVersion();
        String JavaVersion =  ManagementFactory.getRuntimeMXBean().getSpecVersion();
        new BukkitMsgBuilder("&8╠═ &7JVM: &3" + JVMName + " &7- &3" + JVMVendor + " &7- &3" + JVMVersion + " &7(&3Java "+JavaVersion+"&7)").send(player);
        // Processors
        new BukkitMsgBuilder("&8╠═ &7CPU Processors: &3" + Runtime.getRuntime().availableProcessors()).send(player);
        // Threads
        new BukkitMsgBuilder("&8╠═ &7Active Threads: &3" + ManagementFactory.getThreadMXBean().getThreadCount()).send(player);
        // Flags
        new BukkitMsgBuilder("&8╠═ &7Flags: &3" + String.join(", ", ManagementFactory.getRuntimeMXBean().getInputArguments())).send(player);
    }
}
