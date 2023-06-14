package me.itsmcb.drusk.features.creative;

import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.utils.MathUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class WhatToBuildCmd extends CustomCommand {
    public WhatToBuildCmd() {
        super("whattobuild", "Get a suggestion on what you can build.", "drusk.whattobuild");
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        List<String> styles = List.of("Industrial","Scandanavian","Candyland","Ancient Egyptian","Ancient Greek","Ancient Roman","Baroque","Gothic","Futuristic","Brutalist","Alien","Psychodelic","Post-Apocalyptic","Wild West","Surrealistic","Futuristic","Sci-Fi","Pirate","Art Deco","Romanesque","Hindu","Rococo","Dystopian Future");
        List<String> punkStyles = List.of("Steampunk","Biopunk","Futurepunk","Solarpunk","Dieselpunk","Ecopunk");
        List<String> franchises = List.of("Halo","Borderlands");
        List<List<String>> styleList = List.of(styles, punkStyles,franchises);
        List<String> randomStyleList = styleList.get(MathUtils.randomIntBetween(0, styleList.size()-1));
        String randomStyle = randomStyleList.get(MathUtils.randomIntBetween(0,randomStyleList.size()-1));

        List<String> buildings = List.of("Home","Villa","Bridge","Castle","Palace","Police Station","Prison","Storehouse","Factory","Fortress","University","Statue","Haunted Manor","Battleship Carrier","Theatre","Gazebo","Football Stadium","Temple","Laboratory");
        List<String> areaStructures = List.of("Village","Town Square","Marketplace","City");
        List<String> things = List.of("Defense Line","Robot","Interior","Creature");
        List<String> vehicles = List.of("Spaceship","Car","Tank","Train");
        List<List<String>> thingList = List.of(buildings,things,vehicles,areaStructures);
        List<String> randomThingList = thingList.get(MathUtils.randomIntBetween(0, thingList.size()-1));
        String randomThing = randomThingList.get(MathUtils.randomIntBetween(0, randomThingList.size()-1));

        new BukkitMsgBuilder("&7Your randomly generated build is a &d"+randomStyle+"&7 style &d"+randomThing).send(player);
    }
}
