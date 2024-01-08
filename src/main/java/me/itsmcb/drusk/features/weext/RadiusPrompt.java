package me.itsmcb.drusk.features.weext;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RadiusPrompt extends ValidatingPrompt {
    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return "Please type a radius into chat (integer number like 20).";
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext c, @NotNull String s) {
        Player player = (Player) c.getForWhom();
        WERun.runWECmd(c.getSessionData("cmd")+s,player);
        return END_OF_CONVERSATION;
    }

    @Override
    protected @Nullable String getFailedValidationText(@NotNull ConversationContext context, @NotNull String invalidInput) {
        return "You did not type in an integer number. Try again (or quit with \"exit\"";
    }
}
