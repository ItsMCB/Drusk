
package me.itsmcb.drusk.features.nickname;

import me.itsmcb.drusk.Drusk;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NicknamePrompt extends ValidatingPrompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return "Please input a new nickname (1-25 characters) or reset it with \"reset\"";
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String s) {
        return isValidNickname(s);
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext c, @NotNull String s) {
        Player player = (Player) c.getForWhom();
        new NicknameCmd((Drusk) c.getSessionData("drusk")).executeAsPlayer(player,new String[]{s});
        return END_OF_CONVERSATION;
    }

    @Override
    protected @Nullable String getFailedValidationText(@NotNull ConversationContext context, @NotNull String invalidInput) {
        return "Your input must be between 1-25 characters";
    }

    public static boolean isValidNickname(String s) {
        return !s.isEmpty() && s.length() < 26; // 1-25 characters
    }
}
