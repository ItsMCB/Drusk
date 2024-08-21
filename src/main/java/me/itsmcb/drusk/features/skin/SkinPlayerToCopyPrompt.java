
package me.itsmcb.drusk.features.skin;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.drusk.features.skin.copy.UsernameSCmd;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkinPlayerToCopyPrompt extends ValidatingPrompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return "Please enter a valid username to copy";
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext c, @NotNull String input) {
        return input.length() > 2 && input.length() < 17;
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext c, @NotNull String input) {
        Player player = (Player) c.getForWhom();
        new UsernameSCmd((Drusk) c.getSessionData("drusk")).executeAsPlayer(player,new String[]{input});
        return END_OF_CONVERSATION;
    }
}
