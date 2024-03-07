package me.itsmcb.drusk.features.logger;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class LoggerFeat extends BukkitFeature {

    private Drusk instance;
    private JDA jda;

    public LoggerFeat(Drusk instance) {
        super("Discord Event Logger", "Log information to Discord.", "logging", instance);
        this.instance = instance;
    }

    @Override
    public void enablePreLoadTriggers() {
        String channelID = instance.getMainConfig().get().getString("logging.channel-id");
        if (channelID.isEmpty()) {
            System.out.println("Channel ID must be provided for logger to work!");
            return;
        }
        try {
            jda = JDABuilder.createDefault(instance.getMainConfig().get().getString("logging.token")).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
            registerListener(new EventsForLogging(instance,jda,channelID));
        } catch (Exception e) {
            System.out.println("The Discord bot couldn't be loaded! Error:\n"+e.getMessage());
        }
    }

    @Override
    public void disableTriggers() {
        if (jda != null) {
            jda.shutdownNow();
        }
    }
}
