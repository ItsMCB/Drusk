package me.itsmcb.drusk.features.text;

import libs.dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import me.clip.placeholderapi.PlaceholderAPI;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PluginUtils;
import me.itsmcb.vexelcore.common.api.config.BoostedConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class OpenTextCmd extends CustomCommand {

    private Drusk instance;

    public OpenTextCmd(Drusk instance) {
        super("opentext", "Open a server text as a book", "drusk.book.open");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        if (args.length < 1) {
            new BukkitMsgBuilder("&cPlease provide a file name!").send(player);
            return;
        }
        Optional<String> file = instance.getTexts().stream().filter(text -> text.equals(args[0])).findFirst();
        if (file.isEmpty()) {
            new BukkitMsgBuilder("&cText could not be found!").send(player);
            return;
        }
        BoostedConfig config = new BoostedConfig(new File(instance.getDataFolder() + File.separator + "texts"), file.get(), null, new SpigotSerializer());

        // Check if sections exist
        // TODO for some reason placeholders are changed
        List<List<BukkitMsgBuilder>> sections = (List<List<BukkitMsgBuilder>>) config.get().getList("sections");

        // Parse placeholders if PAPI is installed
        List<List<BukkitMsgBuilder>> finalSections = sections.stream().map(s -> s.stream().map(ss -> (PluginUtils.pluginIsLoaded("PlaceholderAPI") ? ss.messageText(PlaceholderAPI.setPlaceholders(player,ss.getMessageText())) : ss)).toList()).toList();

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        // Must set title and author to avoid "invalid book tag" error
        bookMeta.title(new BukkitMsgBuilder("Book").get());
        bookMeta.author(new BukkitMsgBuilder("Server").get());

        finalSections.forEach(section -> {
            AtomicReference<TextComponent> textComponent = new AtomicReference<>(Component.text(""));
            section.forEach(component -> {
                textComponent.set(textComponent.get().append(component.get()));
            });
            bookMeta.addPages(textComponent.get());
        });

        book.setItemMeta(bookMeta);
        player.openBook(book);
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return instance.getTexts();
    }
}
