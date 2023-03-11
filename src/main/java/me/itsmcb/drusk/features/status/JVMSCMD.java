package me.itsmcb.drusk.features.status;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
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
        new BukkitMsgBuilder("&7========== &3&lJVM &r&7==========").send(player);
        // Memory
        double memoryUsed = ((Runtime.getRuntime().totalMemory()/1048576f)-(Runtime.getRuntime().freeMemory()/1048576f));
        double memoryTotal = (Runtime.getRuntime().totalMemory()/1048576f);
        double memoryUsedPercent = ((memoryUsed/memoryTotal)*100);
        DecimalFormat percentDecimalFormat = new DecimalFormat("#.##");
        new BukkitMsgBuilder("&3Memory Usage: &b"+ percentDecimalFormat.format(memoryUsedPercent) + "% &7(&b" + percentDecimalFormat.format(memoryUsed) + " mb&7/&b"+percentDecimalFormat.format(memoryTotal)+" mb&7)")
                .send(player);
        // Uptime
        new BukkitMsgBuilder("&3Uptime: &b" + ManagementFactory.getRuntimeMXBean().getUptime() + " milliseconds").send(player);
        // Platform
        new BukkitMsgBuilder("&3Platform: &b" + System.getProperty("os.arch") + " &7(&b"+System.getProperty("os.name")+"&7)").send(player);
        // JVM
        String JVMName = ManagementFactory.getRuntimeMXBean().getVmName();
        String JVMVendor = ManagementFactory.getRuntimeMXBean().getVmVendor();
        String JVMVersion = ManagementFactory.getRuntimeMXBean().getVmVersion();
        String JavaVersion =  ManagementFactory.getRuntimeMXBean().getSpecVersion();
        new BukkitMsgBuilder("&3JVM: &b" + JVMName + " &7- &b" + JVMVendor + " &7- &bbuild " + JVMVersion + " &7(&bJava "+JavaVersion+"&7)").send(player);
        // Threads
        new BukkitMsgBuilder("&3Threads Running: &b" + Thread.getAllStackTraces().size());
        // Flags
        new BukkitMsgBuilder("&3Flags: &b" + String.join(", ", ManagementFactory.getRuntimeMXBean().getInputArguments())).send(player);

    }
}
