package me.itsmcb.drusk.features.creative;

import me.itsmcb.drusk.DruskAPI;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import org.bukkit.entity.Player;

public class WhatToBuildCmd extends CustomCommand {
    public WhatToBuildCmd() {
        super("whattobuild", "Generate a random build idea.", "drusk.whattobuild");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        new BukkitMsgBuilder("&7Here are three randomly generated build ideas:" +
                "\n- &d"+ DruskAPI.randomBuildStyle() +"&7 style &d"+DruskAPI.randomBuildThing() +
                "&7\n- &d"+ DruskAPI.randomBuildStyle() +"&7 style &d"+DruskAPI.randomBuildThing() +
                "&7\n- &d"+ DruskAPI.randomBuildStyle() +"&7 style &d"+DruskAPI.randomBuildThing()
        ).send(player);
    }
}
