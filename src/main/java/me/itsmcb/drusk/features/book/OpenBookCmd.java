package me.itsmcb.drusk.features.book;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.PaginatedMenu;
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
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class OpenBookCmd extends CustomCommand {

    private Drusk instance;

    public OpenBookCmd(Drusk instance) {
        super("openbook", "Handle server books", "drusk.book.open");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        if (args.length < 1) {
            new BukkitMsgBuilder("&cPlease provide a file name!").send(player);
            return;
        }
        Optional<BoostedConfig> textConfig = instance.getTexts().stream().filter(text -> text.getFileName().equals(args[0])).findFirst();
        if (textConfig.isEmpty()) {
            new BukkitMsgBuilder("&cText could not be found!").send(player);
            return;
        }
        // Check if sections exist
        List<List<BukkitMsgBuilder>> sections = (List<List<BukkitMsgBuilder>>) textConfig.get().get().getList("sections");

        // Check if Bedrock player or not and send data differently accordingly
        if (PluginUtils.pluginIsLoaded("floodgate")) {
            FloodgateApi api = FloodgateApi.getInstance();
            if (api.isFloodgatePlayer(player.getUniqueId())) {
                // Send as menu because Geyser players don't support virtual books
                MenuV2 menu = new PaginatedMenu("Click Paper to View Text in Chat",36,player);
                sections.forEach(section -> {
                    ArrayList<TextComponent> components = new ArrayList<>();
                    section.forEach(subsection -> {
                        components.add(subsection.get());
                    });
                    menu.addItem(new MenuV2Item(Material.PAPER).name("&7Click to view this message in chat").addLore(components).leftClickAction(e -> {
                        components.forEach(player::sendMessage);
                        player.closeInventory();
                    }));
                });
                instance.getMenuManager().open(menu,player);
                return;
            }
        }

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        // Must set title and author to avoid "invalid book tag" error
        bookMeta.title(new BukkitMsgBuilder("Title").get());
        bookMeta.author(new BukkitMsgBuilder("Author").get());

        sections.forEach(section -> {
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
        return instance.getTexts().stream().map(BoostedConfig::getFileName).toList();
    }
}
