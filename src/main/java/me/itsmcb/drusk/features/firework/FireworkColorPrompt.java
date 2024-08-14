
package me.itsmcb.drusk.features.firework;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FireworkColorPrompt extends ValidatingPrompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return "Please input a hex color code (ex. \"#ff00fb\")";
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
        try {
            return TextColor.fromHexString(input) != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext c, @NotNull String s) {
        TextColor tc = TextColor.fromHexString(s); // Test #ff00fb
        Color color = Color.fromRGB(tc.red(),tc.green(),tc.blue());
        boolean isRegularColor = (boolean) c.getSessionData("isRegularColor");
        FireworkEffect.Builder builder = (FireworkEffect.Builder) c.getSessionData("builder");
        Consumer<FireworkEffect.Builder> consumer = (Consumer<FireworkEffect.Builder>) c.getSessionData("consumer");
        if (isRegularColor) {
            builder.withColor(color);
        } else {
            builder.withFade(color);
        }
        consumer.accept(builder);
        return END_OF_CONVERSATION;
    }

    @Override
    protected @Nullable String getFailedValidationText(@NotNull ConversationContext context, @NotNull String invalidInput) {
        return "Your input must be a hex color code!";
    }
}
