package me.itsmcb.drusk.features.character;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.drusk.features.nickname.NicknameCmd;
import me.itsmcb.drusk.features.skin.SkinPlayerToCopyPrompt;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.conversation.InputPrefix;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.PaginatedMenu;
import me.itsmcb.vexelcore.bukkit.api.menuv2.SkullBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import me.itsmcb.vexelcore.bukkit.plugin.CachedPlayer;
import me.itsmcb.vexelcore.common.api.HeadTexture;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.Optional;

public class CharacterCmd extends CustomCommand {
    private Drusk instance;

    public CharacterCmd(Drusk instance) {
        super("char","Create, use, and share characters","drusk.character");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        String currentNickname = new NicknameCmd(instance).getNickname(player.getUniqueId());
        // TODO check characters first, then player cache
        Optional<CachedPlayer> cpo = instance.getCacheManager().getAllFromFile().stream().filter(cp -> cp.getPlayerSkin().getSkinURL().equals(player.getPlayerProfile().getTextures().getSkin())).findFirst();
        MenuV2 menu = new MenuV2("&8Character Editor", InventoryType.HOPPER,5).clickCloseMenu(true)
                .addItem(new MenuV2Item(Material.NAME_TAG).slot(0).name("&3Nickname")
                        .addLore(
                                "&7Type a new nickname into chat","",
                                "&3ℹ &7Currently: &3"+
                                        (currentNickname == null ? "None set" : currentNickname)
                                , "",
                                "&3➤ &7Click to change"
                        )
                        .clickAction(e -> {
                            new NicknameCmd(instance).executeAsPlayer(player);
                        }))
                .addItem(new SkullBuilder(instance.getCacheManager().get(player).getPlayerSkin().getValue()).slot(1).name("&3Skin")
                        .addLore(
                                "&7Change how your skin is displayed on this server","",
                                "&3ℹ &7Currently: &3"+
                                        (cpo.isEmpty() ? "None set" : cpo.get().getName()+" &8(Player)")
                                ,"",
                                "&3➤ &7Click to customize"
                        )
                        .clickAction(e -> {
                            instance.getMenuManager().open(skinEditor(player),player);
                        }))
                .addItem(new MenuV2Item(Material.PLAYER_HEAD).slot(2).name("&3Your Characters")
                        .addLore("&7Characters you've saved")
                        .clickAction(e -> {
                            instance.getMenuManager().open(yourCharacters(player),player);
                        }))
                .addItem(new MenuV2Item(Material.PLAYER_HEAD).slot(3).name("&3Community Characters")
                        .addLore("&7Characters published by the community").clickAction(e -> {
                            //instance.getMenuManager().open(communityCharacters(player),player);
                        }))
                .addItem(new SkullBuilder(HeadTexture.RED_X.getTexture()).slot(4).name("&3Reset")
                        .addLore(
                                "&7Remove customization of skin and display name","",
                                "&3➤ &7Click to reset"
                        )
                        .clickAction(e -> {
                            new NicknameCmd(instance).executeAsPlayer(player,new String[]{"reset"});
                            CachedPlayer cp = instance.getCacheManager().get(player);
                            PlayerUtils.setSkin(player,cp.getPlayerSkin().getValue(),cp.getPlayerSkin().getSignature());
                        }));
        instance.getMenuManager().open(menu,player);
    }

    public MenuV2 skinEditor(Player player) {
        MenuV2 menu = new MenuV2("&8Skin Editor",InventoryType.HOPPER,5).clickCloseMenu(true)
                .addItem(new SkullBuilder(player).slot(0).name("&3Current Skin")
                        .clickAction(e -> {
                            // TODO return character name if identified (+ value and signature?)
                        }))
                .addItem(new MenuV2Item(Material.NAME_TAG).slot(2).name("&3Copy by Username")
                        .addLore("&7Type a valid username into chat to wear their skin")
                        .clickAction(e -> {
                            Conversation c = new ConversationFactory(instance)
                                    .withFirstPrompt(new SkinPlayerToCopyPrompt())
                                    .withEscapeSequence("exit")
                                    .withTimeout(60)
                                    .withPrefix(new InputPrefix())
                                    .withLocalEcho(false)
                                    .buildConversation(player);
                            c.getContext().setSessionData("drusk",instance);
                            c.begin();
                        }));
        return menu;
    }

    public MenuV2 yourCharacters(Player player) {
        MenuV2 menu = new PaginatedMenu("&8Your Characters",27,player).clickCloseMenu(true);
        // TODO iterate through characters
        return menu;
    }

    public MenuV2 communityCharacters(Player player) {
        MenuV2 menu = new PaginatedMenu("&8Your Characters",27,player).clickCloseMenu(true);
        // TODO iterate through characters
        return menu;
    }
}
