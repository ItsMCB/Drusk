package me.itsmcb.drusk;

import me.itsmcb.vexelcore.common.api.utils.MathUtils;

import java.util.List;

public class DruskAPI {
    public static String randomBuildStyle() {
        List<String> styles = List.of("Industrial","Scandanavian","Candyland","Ancient Egyptian","Ancient Greek","Ancient Roman","Baroque","Gothic","Futuristic","Brutalist","Alien","Psychodelic","Post-Apocalyptic","Wild West","Surrealistic","Futuristic","Sci-Fi","Pirate","Art Deco","Romanesque","Hindu","Rococo","Dystopian Future");
        List<String> punkStyles = List.of("Steampunk","Biopunk","Futurepunk","Solarpunk","Dieselpunk","Ecopunk");
        List<String> franchises = List.of("Halo","Borderlands");
        List<List<String>> styleList = List.of(styles, punkStyles,franchises);
        List<String> randomStyleList = styleList.get(MathUtils.randomIntBetween(0, styleList.size()-1));
        return randomStyleList.get(MathUtils.randomIntBetween(0,randomStyleList.size()-1));
    }

    public static String randomBuildThing() {
        List<String> buildings = List.of("Home","Villa","Bridge","Castle","Palace","Police Station","Prison","Storehouse","Factory","Fortress","University","Statue","Haunted Manor","Battleship Carrier","Theatre","Gazebo","Football Stadium","Temple","Laboratory");
        List<String> areaStructures = List.of("Village","Town Square","Marketplace","City","Farm","River","Pond");
        List<String> things = List.of("Defense Line","Robot","Interior","Creature");
        List<String> vehicles = List.of("Spaceship","Car","Tank","Train");
        List<List<String>> thingList = List.of(buildings,things,vehicles,areaStructures);
        List<String> randomThingList = thingList.get(MathUtils.randomIntBetween(0, thingList.size()-1));
        return randomThingList.get(MathUtils.randomIntBetween(0, randomThingList.size()-1));
    }
}
