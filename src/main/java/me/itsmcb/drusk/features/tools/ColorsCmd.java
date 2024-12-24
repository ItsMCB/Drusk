package me.itsmcb.drusk.features.tools;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.ChatUtils;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ColorsCmd extends CustomCommand {
    public ColorsCmd() {
        super("colors", "View color and formatting codes", "drusk.colors");
        registerSubCommand(new ColorViewSCmd());
    }

    @Override
    public void executeAsAnyCommandSender(CommandSender sender, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (args.length == 0) {
            sender.sendMessage(ColorViewSCmd.getColorExamplesMsg());
        }
        String input = cmdHelper.getStringOfArgsAfterIndex(-1);
        sender.sendMessage(ChatUtils.getColorizer().deserialize(input).hoverEvent(Component.text(input)));
    }

}
