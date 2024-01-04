package me.itsmcb.drusk.features.skin;

import libs.dev.dejvokep.boostedyaml.block.implementation.Section;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.PaginatedMenu;
import me.itsmcb.vexelcore.bukkit.api.menuv2.SkullBuilder;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;

public class SelectSCmd extends CustomCommand {

    private Drusk instance;

    public SelectSCmd(Drusk instance) {
        super("select", "Select a skin from a list", "drusk.skin");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        // Costume Data
        Section category = instance.getCostumes().get().getSection("category");
        List<DruskCostume> demoCostumes = (List<DruskCostume>) category.getList("demo");
        List<DruskCostume> materialCostumes = (List<DruskCostume>) category.getList("materials");
        List<DruskCostume> skinPackDefault = (List<DruskCostume>) category.getList("legacy_console_skin_pack_default");
        List<DruskCostume> skinPackOne = (List<DruskCostume>) category.getList("legacy_console_skin_pack_one");
        List<DruskCostume> ourGiftToYou = (List<DruskCostume>) category.getList("our_gift_to_you");
        List<DruskCostume> special = (List<DruskCostume>) category.getList("special");
        List<DruskCostume> summerOfArcade = (List<DruskCostume>) category.getList("legacy_console_summer_of_arcade");
        List<DruskCostume> skinPackTwo = (List<DruskCostume>) category.getList("legacy_console_skin_pack_two");

        MenuV2 demoMenu = new PaginatedMenu("Random",36, player);
        addSkins(demoCostumes, demoMenu, player);

        MenuV2 materialsMenu = new PaginatedMenu("Materials",36, player);
        addSkins(materialCostumes, materialsMenu, player);

        MenuV2 skinPackDefaultMenu = new PaginatedMenu("Default",36, player);
        addSkins(skinPackDefault, skinPackDefaultMenu, player);

        MenuV2 summerOfArcadeMenu = new PaginatedMenu("Summer of Arcade",36, player);
        addSkins(summerOfArcade, summerOfArcadeMenu, player);

        MenuV2 skinPackOneMenu = new PaginatedMenu("Skin Pack One",36, player);
        addSkins(skinPackOne, skinPackOneMenu, player);

        MenuV2 skinPackTwoMenu = new PaginatedMenu("Skin Pack Two",36, player);
        addSkins(skinPackTwo, skinPackTwoMenu, player);

        MenuV2 ourGiftToYouMenu = new PaginatedMenu("Our Gift to You",36, player);
        addSkins(ourGiftToYou, ourGiftToYouMenu, player);

        MenuV2 specialMenu = new PaginatedMenu("Special",36, player);
        addSkins(special, specialMenu, player);

        // Main Menu
        String amongUsCharacter = "ewogICJ0aW1lc3RhbXAiIDogMTY3OTgwNjU3NzgzNywKICAicHJvZmlsZUlkIiA6ICI3ODEwY2MzZWJmZjU0MmZjOTM4OTQ2YmZiODE3OWM2YSIsCiAgInByb2ZpbGVOYW1lIiA6ICJNdmFyYyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80NGIyNDY4NGQyZGFkZTBhMDYwMTdlNzI5M2M5ZTdlNjRhNTlhNmQ2YWU2M2ExMTA3MmMwNzhhMDUyYWFjM2Q2IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        String ironOre = "ewogICJ0aW1lc3RhbXAiIDogMTY3OTIzMjYzMzU4MiwKICAicHJvZmlsZUlkIiA6ICJhYTk3N2E3ODk0NTE0ODdhOGZiOWVhYmI0NzcyYmNmMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJiZXN0c3VuZyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82NzBjZGYwMzFjYzllMzc5MjcyMjhhMmU5NTMyNGEzNzViZThhZmU1ZTMwZTE5M2Y5NTIzMzI3MTk3YWI1Y2UwIgogICAgfQogIH0KfQ";
        String knightTemplar = "ewogICJ0aW1lc3RhbXAiIDogMTY3OTgyNDY2OTQ1OSwKICAicHJvZmlsZUlkIiA6ICI1ODkwNjAyNDYyMzE0ZGFjODM0NWQ3YjI4MmExZDI4ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJXeW5uY3JhZnRHYW1pbmciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIxMzExNGI2NjhiZDljMTU0Mjk0MTA4MWE3NGY3MjUwOWZlMmI4NjhhODY2MWU0YmJjYTY4OGViYzk3MmY4NCIKICAgIH0KICB9Cn0";
        String boxerSteve = "ewogICJ0aW1lc3RhbXAiIDogMTYyNjY0NDY4NDI3OCwKICAicHJvZmlsZUlkIiA6ICIyZjllYWI5OGNmZmE0ZWQ1YmU4ZTMwYTc2OTM2MmFkYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJFeHBhbmREb25nNSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iMjI2Nzk3ZjNkNGU5MjAxMTllMWE4NzBmZTRhZjFmZTViYWIwMDUxMzQ2MzkwMDRjOTQ0Mjk3YjU4MGIwY2U2IgogICAgfQogIH0KfQ";
        String edmond = "ewogICJ0aW1lc3RhbXAiIDogMTYzNjM2MTU3NjAxMiwKICAicHJvZmlsZUlkIiA6ICJkODAwZDI4MDlmNTE0ZjkxODk4YTU4MWYzODE0Yzc5OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJ0aGVCTFJ4eCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80ZGZhZjc2YTdhODBhMzAyZTc3NWRlYmY5OWQ4YTM3ZGI4MjdiNTE3YzNkMWRlMGI5NDk3N2U0YzAxZDRjNGM0IgogICAgfQogIH0KfQ==";
        String gift = "ewogICJ0aW1lc3RhbXAiIDogMTY2NjcwMDQ4NDAwMCwKICAicHJvZmlsZUlkIiA6ICIwMzdlNzQwNGMyMjk0OTIxOTdkZjBlZWJmYWIyNTNjMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJMX0FfWl9FX1JfIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQwYzAwNzE2MjYxMzBlMmVkYjIyNGRkZWE1MjYwOWNjOTNjYWY0NzFlOTk1ODRhOGVjMjRmMTQ1MGQxYmE3MWYiCiAgICB9CiAgfQp9";
        String fidget = "ewogICJ0aW1lc3RhbXAiIDogMTU4OTUxNDExMzQ3OSwKICAicHJvZmlsZUlkIiA6ICI4MmM2MDZjNWM2NTI0Yjc5OGI5MWExMmQzYTYxNjk3NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJOb3ROb3RvcmlvdXNOZW1vIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzE4Yjc5Njc2ZmMzYmI3Zjk3MjEwNTRkNTgwZGE4MzY5OTA0MzYwYWUzOTExYjIwMTRjYjgzZjg0ZmYwYTQwOCIKICAgIH0KICB9Cn0";
        String redKnight = "ewogICJ0aW1lc3RhbXAiIDogMTY4NzI3MTIyODkwMSwKICAicHJvZmlsZUlkIiA6ICJjYjYxY2U5ODc4ZWI0NDljODA5MzliNWYxNTkwMzE1MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJWb2lkZWRUcmFzaDUxODUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ5ZDZhNThlMmJkYWViOGQ2NjY3NmNlNzQ1NGQxNmMwYTdiYWFlYTViMjIzNmYxZDBiYjY2YWEyYWJkNzU3MCIKICAgIH0KICB9Cn0";

        MenuV2 selectMenu = new MenuV2("Skin Packs", InventoryType.CHEST,27);
                selectMenu.addItem(new SkullBuilder(amongUsCharacter).name("&aRandom").slot(2).leftClickAction(event -> {
                    instance.getMenuManager().open(demoMenu, player, selectMenu);
                }))
                .addItem(new SkullBuilder(ironOre).name("&aMaterials").slot(4).leftClickAction(event -> {
                    instance.getMenuManager().open(materialsMenu, player, selectMenu);
                }))
                .addItem(new SkullBuilder(edmond).name("&aSpecial").slot(6).leftClickAction(event -> {
                    instance.getMenuManager().open(specialMenu, player, selectMenu);
                }))
                .addItem(new SkullBuilder(boxerSteve).name("&aDefault").addLore("&7Legacy Console","&7Released July 13, 2012").slot(10).leftClickAction(event -> {
                    instance.getMenuManager().open(skinPackDefaultMenu, player, selectMenu);
                }))
                .addItem(new SkullBuilder(fidget).name("&aSummer of Arcade").addLore("&7Legacy Console").slot(12).leftClickAction(event -> {
                    instance.getMenuManager().open(summerOfArcadeMenu, player, selectMenu);
                }))
                .addItem(new SkullBuilder(knightTemplar).name("&aSkin Pack One").addLore("&7Legacy Console").slot(14).leftClickAction(event -> {
                    instance.getMenuManager().open(skinPackOneMenu, player, selectMenu);
                }))
                .addItem(new SkullBuilder(redKnight).name("&aSkin Pack Two").addLore("&7Legacy Console").slot(16).leftClickAction(event -> {
                    instance.getMenuManager().open(skinPackTwoMenu, player, selectMenu);
                }))
                .addItem(new SkullBuilder(gift).name("&aOur Gift to You").addLore("&7Bedrock Marketplace &8| &757Digital").slot(22).leftClickAction(event -> {
                    instance.getMenuManager().open(ourGiftToYouMenu, player, selectMenu);
                }));
        instance.getMenuManager().open(selectMenu, player);
    }

    private void addSkins(List<DruskCostume> costumes, MenuV2 menu, Player player) {
        costumes.forEach(costume -> {
            MenuV2Item item = new SkullBuilder(costume.getValue()).name("&r&a"+costume.getName());
            if (costume.hasDescription()) {
                item.lore(List.of(new BukkitMsgBuilder(costume.getDescription()).get()));
            }
            item.addLore(new BukkitMsgBuilder("&7").get(),new BukkitMsgBuilder("&7Click to apply skin").get());
            menu.addItem(item.leftClickAction(event -> {
                PlayerUtils.setSkin(player, costume.getValue(),costume.getSignature());
                new BukkitMsgBuilder("&7Now wearing skin &d"+costume.getName()).send(player);
            }));
        });
    }

}
