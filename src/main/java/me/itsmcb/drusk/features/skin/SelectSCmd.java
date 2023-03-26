package me.itsmcb.drusk.features.skin;

import libs.dev.dejvokep.boostedyaml.block.implementation.Section;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.menuv2.*;
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

        MenuV2 demoMenu = new PaginatedMenu("Select Skin To Copy",36, player);
        addSkins(demoCostumes, demoMenu, player);

        MenuV2 materialsMenu = new PaginatedMenu("Select Skin To Copy",36, player);
        addSkins(materialCostumes, materialsMenu, player);

        MenuV2 skinPackDefaultMenu = new PaginatedMenu("Select Skin To Copy",36, player);
        addSkins(skinPackDefault, skinPackDefaultMenu, player);

        MenuV2 skinPackOneMenu = new PaginatedMenu("Select Skin To Copy",36, player);
        addSkins(skinPackOne, skinPackOneMenu, player);

        // Main Menu
        String amongUsCharacter = "ewogICJ0aW1lc3RhbXAiIDogMTY3OTgwNjU3NzgzNywKICAicHJvZmlsZUlkIiA6ICI3ODEwY2MzZWJmZjU0MmZjOTM4OTQ2YmZiODE3OWM2YSIsCiAgInByb2ZpbGVOYW1lIiA6ICJNdmFyYyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80NGIyNDY4NGQyZGFkZTBhMDYwMTdlNzI5M2M5ZTdlNjRhNTlhNmQ2YWU2M2ExMTA3MmMwNzhhMDUyYWFjM2Q2IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
        String ironOre = "ewogICJ0aW1lc3RhbXAiIDogMTY3OTIzMjYzMzU4MiwKICAicHJvZmlsZUlkIiA6ICJhYTk3N2E3ODk0NTE0ODdhOGZiOWVhYmI0NzcyYmNmMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJiZXN0c3VuZyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82NzBjZGYwMzFjYzllMzc5MjcyMjhhMmU5NTMyNGEzNzViZThhZmU1ZTMwZTE5M2Y5NTIzMzI3MTk3YWI1Y2UwIgogICAgfQogIH0KfQ";
        String knightTemplar = "ewogICJ0aW1lc3RhbXAiIDogMTY3OTgyNDY2OTQ1OSwKICAicHJvZmlsZUlkIiA6ICI1ODkwNjAyNDYyMzE0ZGFjODM0NWQ3YjI4MmExZDI4ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJXeW5uY3JhZnRHYW1pbmciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIxMzExNGI2NjhiZDljMTU0Mjk0MTA4MWE3NGY3MjUwOWZlMmI4NjhhODY2MWU0YmJjYTY4OGViYzk3MmY4NCIKICAgIH0KICB9Cn0";
        String boxerSteve = "ewogICJ0aW1lc3RhbXAiIDogMTYyNjY0NDY4NDI3OCwKICAicHJvZmlsZUlkIiA6ICIyZjllYWI5OGNmZmE0ZWQ1YmU4ZTMwYTc2OTM2MmFkYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJFeHBhbmREb25nNSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iMjI2Nzk3ZjNkNGU5MjAxMTllMWE4NzBmZTRhZjFmZTViYWIwMDUxMzQ2MzkwMDRjOTQ0Mjk3YjU4MGIwY2U2IgogICAgfQogIH0KfQ";
        MenuV2 selectMenu = new MenuV2("Choose Selection")
                .addItem(new MenuV2Item(new SkullBuilder(amongUsCharacter).name("&aRandom")).slot(10).leftClickAction(event -> {
                    instance.getMenuManager().open(demoMenu, player);
                }))
                .addItem(new MenuV2Item(new SkullBuilder(ironOre).name("&aMaterials")).slot(12).leftClickAction(event -> {
                    instance.getMenuManager().open(materialsMenu, player);
                }))
                .addItem(new MenuV2Item(new SkullBuilder(boxerSteve).name("&aLegacy Console Default Skins")).slot(14).leftClickAction(event -> {
                    instance.getMenuManager().open(skinPackDefaultMenu, player);
                }))
                .addItem(new MenuV2Item(new SkullBuilder(knightTemplar).name("&aLegacy Console Skin Pack One")).slot(16).leftClickAction(event -> {
                    instance.getMenuManager().open(skinPackOneMenu, player);
                }));
        instance.getMenuManager().open(selectMenu, player);
    }

    private void addSkins(List<DruskCostume> costumes, MenuV2 menu, Player player) {
        costumes.forEach(costume -> {
            ItemBuilder item = new SkullBuilder(costume.getValue()).name("&r&a"+costume.getName());
            if (costume.hasDescription()) {
                item.lore(List.of(new BukkitMsgBuilder(costume.getDescription()).get()));
            }
            menu.addItem(new MenuV2Item(item).leftClickAction(event -> {
                PlayerUtils.setSkin(player, costume.getValue(),costume.getSignature());
            }));
        });
    }

}
