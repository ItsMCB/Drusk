package me.itsmcb.drusk.features.creative;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.BukkitFeature;

public class CreativeFeat extends BukkitFeature {
    public CreativeFeat(Drusk instance) {
        super("Creative", "Creative server tools", null, instance);
        registerCommand(new WhatToBuildCmd());
    }
}
