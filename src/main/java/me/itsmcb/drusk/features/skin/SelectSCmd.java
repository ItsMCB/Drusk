package me.itsmcb.drusk.features.skin;

import libs.dev.dejvokep.boostedyaml.block.implementation.Section;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.VexelCoreBukkitAPI;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menu.Menu;
import me.itsmcb.vexelcore.bukkit.api.menu.MenuButton;
import me.itsmcb.vexelcore.bukkit.api.menu.MenuRowSize;
import me.itsmcb.vexelcore.bukkit.api.menu.PaginatedMenu;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.bukkit.api.utils.PlayerUtils;
import org.bukkit.entity.Player;

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

        Menu demoMenu = new PaginatedMenu(MenuRowSize.FOUR,"Random");
        addSkins(demoCostumes, demoMenu, player);

        Menu materialsMenu = new PaginatedMenu(MenuRowSize.FOUR,"Materials");
        addSkins(materialCostumes, materialsMenu, player);

        Menu skinPackDefaultMenu = new PaginatedMenu(MenuRowSize.FOUR,"Default");
        addSkins(skinPackDefault, skinPackDefaultMenu, player);

        Menu summerOfArcadeMenu = new PaginatedMenu(MenuRowSize.FOUR,"Summer of Arcade");
        addSkins(summerOfArcade, summerOfArcadeMenu, player);

        Menu skinPackOneMenu = new PaginatedMenu(MenuRowSize.FOUR,"Skin Pack One");
        addSkins(skinPackOne, skinPackOneMenu, player);

        Menu skinPackTwoMenu = new PaginatedMenu(MenuRowSize.FOUR,"Skin Pack Two");
        addSkins(skinPackTwo, skinPackTwoMenu, player);

        Menu ourGiftToYouMenu = new PaginatedMenu(MenuRowSize.FOUR,"Our Gift to You");
        addSkins(ourGiftToYou, ourGiftToYouMenu, player);

        Menu specialMenu = new PaginatedMenu(MenuRowSize.FOUR,"Special");
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

        Menu selectMenu = new Menu(MenuRowSize.THREE,"Skin Packs");
        selectMenu.addButton(2,new MenuButton(amongUsCharacter).name("&aRandom").click(event -> {
            VexelCoreBukkitAPI.getMenuManager().open(demoMenu,player);
            VexelCoreBukkitAPI.getMenuManager().setPreviousMenu(demoMenu,selectMenu);
        }))
        .addButton(4,new MenuButton(ironOre).name("&aMaterials").click(event -> {
            VexelCoreBukkitAPI.getMenuManager().open(materialsMenu,player);
            VexelCoreBukkitAPI.getMenuManager().setPreviousMenu(materialsMenu,selectMenu);
        }))
        .addButton(6,new MenuButton(edmond).name("&aSpecial").click(event -> {
            VexelCoreBukkitAPI.getMenuManager().open(specialMenu,player);
            VexelCoreBukkitAPI.getMenuManager().setPreviousMenu(specialMenu,selectMenu);
        }))
        .addButton(10,new MenuButton(boxerSteve).name("&aDefault").addLore("&7Legacy Console","&7Released July 13, 2012").click(event -> {
            VexelCoreBukkitAPI.getMenuManager().open(skinPackDefaultMenu,player);
            VexelCoreBukkitAPI.getMenuManager().setPreviousMenu(skinPackDefaultMenu,selectMenu);
        }))
        .addButton(12,new MenuButton(fidget).name("&aSummer of Arcade").addLore("&7Legacy Console").click(event -> {
            VexelCoreBukkitAPI.getMenuManager().open(summerOfArcadeMenu,player);
            VexelCoreBukkitAPI.getMenuManager().setPreviousMenu(summerOfArcadeMenu,selectMenu);
        }))
        .addButton(14,new MenuButton(knightTemplar).name("&aSkin Pack One").addLore("&7Legacy Console").click(event -> {
            VexelCoreBukkitAPI.getMenuManager().open(skinPackOneMenu,player);
            VexelCoreBukkitAPI.getMenuManager().setPreviousMenu(skinPackOneMenu,selectMenu);
        }))
        .addButton(16,new MenuButton(redKnight).name("&aSkin Pack Two").addLore("&7Legacy Console").click(event -> {
            VexelCoreBukkitAPI.getMenuManager().open(skinPackTwoMenu,player);
            VexelCoreBukkitAPI.getMenuManager().setPreviousMenu(skinPackTwoMenu,selectMenu);
        }))
        .addButton(22,new MenuButton(gift).name("&aOur Gift to You").addLore("&7Bedrock Marketplace &8| &757Digital").click(event -> {
            VexelCoreBukkitAPI.getMenuManager().open(ourGiftToYouMenu,player);
            VexelCoreBukkitAPI.getMenuManager().setPreviousMenu(ourGiftToYouMenu,selectMenu);
        }));
        VexelCoreBukkitAPI.getMenuManager().open(selectMenu,player);
    }

    private void addSkins(List<DruskCostume> costumes, Menu menu, Player player) {
        costumes.forEach(costume -> {
            MenuButton btn = new MenuButton(costume.getValue()).name("&r&a"+costume.getName());
            if (costume.hasDescription()) {
                btn.addLore(costume.getDescription());
            }
            btn.addLore(new BukkitMsgBuilder("&7").get(),new BukkitMsgBuilder("&7Click to apply skin").get())
            .click(e -> {
                PlayerUtils.setSkin(player, costume.getValue(),costume.getSignature());
                new BukkitMsgBuilder("&7Now wearing skin &d"+costume.getName()).send(player);
            });
            menu.addButton(btn);
        });
    }

}
