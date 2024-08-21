package me.itsmcb.drusk.features.nickname;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.conversation.InputPrefix;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NicknameCmd extends CustomCommand {
    private Drusk instance;

    public NicknameCmd(Drusk instance) {
        super("nick","Change your nickname","drusk.character");
        this.instance = instance;
    }

    public static String saveRoute = "nicknames";

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (args.length == 0) {
            Conversation c = new ConversationFactory(instance)
                    .withFirstPrompt(new NicknamePrompt())
                    .withEscapeSequence("exit")
                    .withTimeout(60)
                    .withPrefix(new InputPrefix())
                    .withLocalEcho(false)
                    .buildConversation(player);
            c.getContext().setSessionData("drusk",instance);
            c.begin();
            return;
        }
        // Setup config
        HashMap<UUID,String> nicknames = getNicknames();
        String nickName = cmdHelper.getStringOfArgsAfterIndex(-1);
        if (NicknamePrompt.isValidNickname(nickName)) {
            // Remove old
            nicknames.remove(player.getUniqueId());
            // Set new if not being reset
            if (nickName.equalsIgnoreCase("reset")) {
                new BukkitMsgBuilder("&7Nickname has been reset. You will now appear as your Minecraft username").send(player);
            } else {
                // Set new nickname
                nicknames.put(player.getUniqueId(),nickName);
                // TODO set name above head using packets at some point
                new BukkitMsgBuilder("&7Nickname changed to &3"+nickName).send(player);
            }
            // Save
            ArrayList<Nickname> nicksToSave = new ArrayList<>();
            nicknames.forEach((u,s) -> nicksToSave.add(new Nickname(u,s)));
            instance.getCharacters().get().set(saveRoute,nicksToSave);
            instance.getCharacters().save();
        }
    }

    @Override
    public List<String> getAdditionalCompletions(CommandSender sender) {
        return List.of("reset");
    }

    private HashMap<UUID,String> getNicknames() {
        ArrayList<Nickname> nicknames = (ArrayList<Nickname>) instance.getCharacters().get().get(saveRoute);

        if (nicknames == null) {
            nicknames = new ArrayList<>();
        }
        HashMap<UUID,String> nicknamesMap = new HashMap<>();
        nicknames.forEach(nn -> {
            nicknamesMap.put(nn.getPlayer(),nn.getName());
        });
        return nicknamesMap;
    }

    public String getNickname(@NotNull UUID uuid) {
        return getNicknames().get(uuid);
    }
}
