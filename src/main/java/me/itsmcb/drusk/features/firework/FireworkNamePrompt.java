
package me.itsmcb.drusk.features.firework;

import me.itsmcb.drusk.Drusk;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireworkNamePrompt extends ValidatingPrompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return "Please enter a name for your firework";
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext c, @NotNull String input) {
        return input.length() < 35;
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext c, @NotNull String input) {
        CustomFirework customFirework = (CustomFirework) c.getSessionData("customFirework");
        CustomFirework modifiedFirework = new CustomFirework(customFirework.getItem(),customFirework.getCreator(),customFirework.isPrivate(), customFirework.getName());
        modifiedFirework.setName(input);
        new FireworkCmd((Drusk) c.getSessionData("drusk")).updateSavedFirework(customFirework,modifiedFirework);
        Player player = (Player) c.getForWhom();
        new FireworkCmd((Drusk) c.getSessionData("drusk")).executeAsPlayer(player,new String[]{});
        return END_OF_CONVERSATION;
    }
}
