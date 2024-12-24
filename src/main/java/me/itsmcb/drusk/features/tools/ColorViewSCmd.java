package me.itsmcb.drusk.features.tools;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ColorViewSCmd extends CustomCommand {

    public ColorViewSCmd() {
        super("view", "View color and formatting codes", "drusk.colors");
    }

    public static Component getColorExamplesMsg() {
        return Component
                .text("Classic Color and Formatting Codes:\n")
                .append(Component.text("&0 ").color(TextColor.color(Color.fromRGB(0,0,0).asRGB())))
                .append(Component.text("&1 ").color(TextColor.color(Color.fromRGB(0,0,170).asRGB())))
                .append(Component.text("&2 ").color(TextColor.color(Color.fromRGB(0,170,0).asRGB())))
                .append(Component.text("&3 ").color(TextColor.color(Color.fromRGB(0,170,170).asRGB())))
                .append(Component.text("&4 ").color(TextColor.color(Color.fromRGB(170,0,0).asRGB())))
                .append(Component.text("&5 ").color(TextColor.color(Color.fromRGB(170,0,170).asRGB())))
                .append(Component.text("&6 ").color(TextColor.color(Color.fromRGB(255,170,0).asRGB())))
                .append(Component.text("&7 ").color(TextColor.color(Color.fromRGB(170,170,170).asRGB())))
                .append(Component.text("&8 ").color(TextColor.color(Color.fromRGB(85,85,85).asRGB())))
                .append(Component.text("&9 ").color(TextColor.color(Color.fromRGB(85,85,255).asRGB())))
                .append(Component.text("&a ").color(TextColor.color(Color.fromRGB(85,255,85).asRGB())))
                .append(Component.text("&b ").color(TextColor.color(Color.fromRGB(85,255,255).asRGB())))
                .append(Component.text("&c ").color(TextColor.color(Color.fromRGB(255,85,85).asRGB())))
                .append(Component.text("&d ").color(TextColor.color(Color.fromRGB(255,85,255).asRGB())))
                .append(Component.text("&e ").color(TextColor.color(Color.fromRGB(255,255,85).asRGB())))
                .append(Component.text("&f ").color(TextColor.color(Color.fromRGB(255,255,255).asRGB())))
                .append(Component.text("\n     "))
                .append(Component.text("&k - "))
                .append(Component.text("hello ").decorate(TextDecoration.OBFUSCATED))
                .append(Component.text("\n     "))
                .append(Component.text("&l - "))
                .append(Component.text("Bold ").decorate(TextDecoration.BOLD))
                .append(Component.text("\n     "))
                .append(Component.text("&m - "))
                .append(Component.text("Strike ").decorate(TextDecoration.STRIKETHROUGH))
                .append(Component.text("\n     "))
                .append(Component.text("&n - "))
                .append(Component.text("Uline ").decorate(TextDecoration.UNDERLINED))
                .append(Component.text("\n     "))
                .append(Component.text("&o - "))
                .append(Component.text("Italic ").decorate(TextDecoration.ITALIC))
                .append(Component.text("\n     "))
                .append(Component.text("&r - "))
                .append(Component.text("Reset"));
    }

    @Override
    public void executeAsAnyCommandSender(CommandSender sender, String[] args) {
        sender.sendMessage(ColorViewSCmd::getColorExamplesMsg);
    }
}
