package me.itsmcb.drusk.features.skin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import libs.dev.dejvokep.boostedyaml.block.implementation.Section;
import libs.dev.dejvokep.boostedyaml.route.Route;
import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.command.CMDHelper;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SaveSCmd extends CustomCommand {

    private Drusk instance;

    public SaveSCmd(Drusk instance) {
        super("save", "Save a skin", "drusk.skin.save");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        CMDHelper cmdHelper = new CMDHelper(args);
        if (cmdHelper.argExists(2)) {
            String name = args[1];
            String description = args[2];
            player.getPlayerProfile().getProperties().forEach(profileProperty -> {
                if (profileProperty.getName().equals("textures")) {
                    System.out.println(profileProperty.getValue());
                    System.out.println(profileProperty.getSignature());
                    Section category = instance.getCostumes().get().getSection("category");
                    ArrayList<DruskCostume> demoCostumes = new ArrayList<>((List<DruskCostume>) category.getList("saved"));
                    demoCostumes.add(new DruskCostume(name,profileProperty.getValue(),profileProperty.getSignature(),description));
                    instance.getCostumes().get().set(Route.fromString("category.saved", '.'),demoCostumes);
                    instance.getCostumes().save();
                    new BukkitMsgBuilder("&dSaved skin as &7"+name).send(player);
                    // Save file
                    try {
                        File skinsFolder = new File(instance.getDataFolder() + File.separator + "skins");
                        skinsFolder.mkdirs();
                        byte[] decodedBytes = Base64.getDecoder().decode(profileProperty.getValue());
                        String decodedString = new String(decodedBytes);
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode jsonNodeRoot = mapper.readTree(decodedString);
                        JsonNode urlNode = jsonNodeRoot.get("textures").get("SKIN").get("url");
                        String url = urlNode.asText();
                        System.out.println("FOUND URL: "+url);
                        InputStream in = new URL(url).openStream();
                        File skinFile = new File(skinsFolder + File.separator + name + ".png");
                        skinFile.createNewFile();
                        Files.copy(in, Paths.get(skinFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }

    }
}
