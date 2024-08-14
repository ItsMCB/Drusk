
package me.itsmcb.drusk.features.firework;

import me.itsmcb.drusk.Drusk;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireworkPowerPrompt extends ValidatingPrompt {
    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return "Sets the approximate power of the firework. Each level of power is half a second of flight time. Value integers are from 0-10";
    }

    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
        try {
            int integer = Integer.parseInt(input);
            return integer < 10 && integer > -1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext c, @NotNull String s) {
        Player player = (Player) c.getForWhom();
        ItemStack rocket = (ItemStack) c.getSessionData("rocket");
        FireworkMeta fwm = (FireworkMeta) rocket.getItemMeta();
        fwm.setPower(Integer.parseInt(s));
        rocket.setItemMeta(fwm);
        new FireworkCmd((Drusk) c.getSessionData("drusk")).rocketEditor(rocket,player);
        return END_OF_CONVERSATION;
    }

    @Override
    protected @Nullable String getFailedValidationText(@NotNull ConversationContext context, @NotNull String invalidInput) {
        return "Your input most be an integer from 0-10!";
    }
}
