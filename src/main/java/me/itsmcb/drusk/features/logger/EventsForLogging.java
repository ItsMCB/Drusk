package me.itsmcb.drusk.features.logger;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.itsmcb.drusk.Drusk;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventsForLogging implements Listener {

    private Drusk instance;
    private JDA jda;
    private String channelID;

    public EventsForLogging(Drusk instance, JDA jda, String channelID) {
        this.instance = instance;
        this.jda = jda;
        this.channelID = channelID;
    }

    public EventsForLogging(Drusk instance) {
        this.instance = instance;
    }

    public MessageChannel getChannel() {
        return jda.getChannelById(MessageChannel.class,channelID);
    }

    public String getAvatarIcon(Player player) {
        return "https://minotar.net/helm/"+player.getUniqueId()+"/64.png";
    }

    @EventHandler
    public void login(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("[+] "+name,"https://libregalaxy.org/mcprofile/"+name,getAvatarIcon(p));
        getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @EventHandler
    public void logout(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("[-] "+name,"https://libregalaxy.org/mcprofile/"+name,getAvatarIcon(p));
        getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @EventHandler
    public void chat(AsyncChatEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(name,"https://libregalaxy.org/mcprofile/"+name,getAvatarIcon(p));
        eb.setDescription(((TextComponent) e.message()).content());
        getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @EventHandler
    public void command(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(name,"https://libregalaxy.org/mcprofile/"+name,getAvatarIcon(p));
        eb.setDescription(e.getMessage());
        getChannel().sendMessageEmbeds(eb.build()).queue();
    }
}
